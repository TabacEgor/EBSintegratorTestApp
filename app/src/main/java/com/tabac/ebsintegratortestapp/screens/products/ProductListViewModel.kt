package com.tabac.ebsintegratortestapp.screens.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.ebsintegratortestapp.BaseViewModel
import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.repository.ProductsRepository
import com.tabac.ebsintegratortestapp.utils.Event
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

    private val _addToCartData = MutableLiveData<Int>()
    val addToCartData: LiveData<Int> = _addToCartData

    private val _errorData = MutableLiveData<Event<String>>()
    val errorData: LiveData<Event<String>> = _errorData

    private val _successData = MutableLiveData<Event<Int>>()
    val successData: LiveData<Event<Int>> = _successData

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

    // TODO remove these duplications in viewModels when cart local database will be implemented
    fun addToCart(product: Product) {
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