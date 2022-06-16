package com.tabac.wowmobileshop.screens.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tabac.wowmobileshop.BaseViewModel
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    private val _addToCartData = MutableLiveData<Int>()
    val addToCartData: LiveData<Int> = _addToCartData

    val favoritesData = productsRepository.getAllFavoriteProducts()

    fun removeFromFavorites(product: Product) {
        launch {
            productsRepository.removeFromFavorites(productId = product.id)
        }
    }

    // TODO remove these duplications in viewModels when cart local database will be implemented
    fun addToCart(product: Product) {
        _addToCartData.postValue(1)
    }
}