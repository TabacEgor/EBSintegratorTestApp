package com.tabac.ebsintegratortestapp.screens.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.ebsintegratortestapp.BaseViewModel
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.repository.ProductsRemoteDataSource
import com.tabac.ebsintegratortestapp.utils.Event
import com.tabac.ebsintegratortestapp.utils.logd
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource
) : BaseViewModel() {

    private val _productsData = MutableLiveData<List<ProductDTO>>()
    val productsData: LiveData<List<ProductDTO>> = _productsData

    private val _errorData = MutableLiveData<Event<String>>()
    val errorData: LiveData<Event<String>> = _errorData

    fun getProducts() {
        launch {
            productsRemoteDataSource.getProducts()
                .onSuccess {
                    _productsData.postValue(it)
                }
                .onFailure {
                    _errorData.postValue(Event(it.message ?: ""))
                }
        }
    }

    fun loadNextPage() {

    }
}