package com.tabac.wowmobileshop.screens.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.wowmobileshop.BaseViewModel
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.repository.ProductsRepository
import com.tabac.wowmobileshop.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _productsData = MutableLiveData<MutableList<Product>>()
    val productsData: LiveData<MutableList<Product>> = _productsData
    val nextPageData = MutableLiveData<Unit>() // TODO Add Jetpack Pagination
    var currentPage = 1

    private val _errorData = MutableLiveData<Event<String>>()
    val errorData: LiveData<Event<String>> = _errorData

    private val _successData = MutableLiveData<Event<Int>>()
    val successData: LiveData<Event<Int>> = _successData

    val cartProductsCountData = productsRepository.getCartProductCount()

    fun getProducts(reloadData: Boolean = true) {
        if (reloadData) resetCurrentPage()
        launch {
            productsRepository.getProducts(currentPage)
                .onSuccess {
                    if (reloadData) {
                        _productsData.postValue(it.toMutableList())
                    } else {
                        _productsData.value?.addAll(it.toMutableList())
                        nextPageData.postValue(Unit)
                    }
                }
                .onFailure {
                    _errorData.postValue(Event(it.message ?: "Something went wrong."))
                    // Backend should return emptyList when page not exist - not 404 error
                }
        }
    }

    fun addProductToFavorite(product: Product, position: Int) {
        launch {
            productsRepository.addToFavorites(product)
                .onSuccess {
                    _successData.postValue(Event(position))
                }.onFailure {
                    _errorData.postValue(Event("Something went wrong. Product not added to favorites"))
                }
        }
    }

    fun addToCart(product: Product) {
        launch {
            productsRepository.addToCart(product)
        }
    }

    fun clearCart() {
        launch {
            productsRepository.removeAllCartProducts()
        }
    }

    fun removeFromFavorites(product: Product, position: Int) {
        launch {
            productsRepository.removeFromFavorites(productId = product.id)
                .onSuccess {
                    _successData.postValue(Event(position))
                }.onFailure {
                    _errorData.postValue(Event("Something went wrong. Product not added to favorites"))
                }
        }
    }

    fun loadNextPage() {
        currentPage++
        getProducts(reloadData = false)
    }

    private fun resetCurrentPage() {
        currentPage = 1
    }
}