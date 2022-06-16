package com.tabac.wowmobileshop.screens.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.wowmobileshop.BaseViewModel
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.repository.CartLocalDataSource
import com.tabac.wowmobileshop.repository.ProductsRemoteDataSource
import com.tabac.wowmobileshop.utils.Event
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