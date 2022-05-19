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

    private val _productsData = MutableLiveData<List<ProductDTO>>()
    val productsData: LiveData<List<ProductDTO>> = _productsData

    private val _errorData = MutableLiveData<Event<String>>()
    val errorData: LiveData<Event<String>> = _errorData

    fun getProducts() {
        launch {
            productsRepository.getProducts()
                .onSuccess {
                    _productsData.postValue(it)
                }
                .onFailure {
                    _errorData.postValue(Event(it.message ?: ""))
                }
        }
    }

    fun addProductToFavorite(product: ProductDTO) {
        launch {
            productsRepository.addToFavorites(product)
        }
    }

    fun loadNextPage() {
        // TODO add pagination
    }
}