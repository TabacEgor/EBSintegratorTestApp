package com.tabac.ebsintegratortestapp.screens.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.ebsintegratortestapp.BaseViewModel
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.repository.ProductsLocalDataSource
import com.tabac.ebsintegratortestapp.repository.ProductsRemoteDataSource
import com.tabac.ebsintegratortestapp.repository.ProductsRepository
import com.tabac.ebsintegratortestapp.utils.Event
import com.tabac.ebsintegratortestapp.utils.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _productsData = MutableLiveData<MutableList<ProductDTO>>()
    val productsData: LiveData<MutableList<ProductDTO>> = _productsData
    val nextPageData = MutableLiveData<Unit>() // TODO Add Jetpack Pagination
    var currentPage = 1

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

    fun addProductToFavorite(product: ProductDTO, position: Int) {
        launch {
            productsRepository.addToFavorites(product)
                .onSuccess {
                    _successData.postValue(Event(position))
                }.onFailure {
                    _errorData.postValue(Event("Something went wrong. Product not added to favorites"))
                }
        }
    }

    fun removeFromFavorites(product: ProductDTO, position: Int) {
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