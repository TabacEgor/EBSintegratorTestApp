package com.tabac.ebsintegratortestapp.screens.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.ebsintegratortestapp.BaseViewModel
import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.repository.CartLocalDataSource
import com.tabac.ebsintegratortestapp.repository.ProductsRemoteDataSource
import com.tabac.ebsintegratortestapp.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val cartLocalDataSource: CartLocalDataSource
) : BaseViewModel() {

    private val _productData = MutableLiveData<Product>()
    val productData: LiveData<Product> = _productData

    private val _errorData = MutableLiveData<Event<String>>()
    val errorData: LiveData<Event<String>> = _errorData

    private val _addToCartData = MutableLiveData<Event<Unit>>()
    val addToCartData: LiveData<Event<String>> = _errorData

    fun getProductDetails(productId: Int) {
        launch {
            productsRemoteDataSource.getProductDetails(productId)
                .onSuccess { _productData.postValue(it) }
                .onFailure { _errorData.postValue(Event(it.message ?: "")) }
        }
    }

    fun addToCart() {
        if(_productData.value == null) return
        launch {
            cartLocalDataSource.addToCart(_productData.value!!)
                .onSuccess { _addToCartData.postValue(Event(Unit)) }
                .onFailure { _errorData.postValue(Event("Something went wrong")) }
        }
    }
}