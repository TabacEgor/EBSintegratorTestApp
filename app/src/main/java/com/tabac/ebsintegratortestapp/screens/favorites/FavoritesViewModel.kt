package com.tabac.ebsintegratortestapp.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.ebsintegratortestapp.BaseViewModel
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    var itemsInCart: Int = 0 // TODO observe items count from local Database cart implementation
    private val _addToCartData = MutableLiveData<Int>()
    val addToCartData: LiveData<Int> = _addToCartData

    val favoritesData = productsRepository.getAllFavoriteProducts()

    fun removeFromFavorites(product: ProductDTO) {
        launch {
            productsRepository.removeFromFavorites(productId = product.id)
        }
    }

    // TODO remove these duplications in viewModels when cart local database will be implemented
    fun addToCart(product: ProductDTO) {
        itemsInCart++
        _addToCartData.postValue(itemsInCart)
    }
}