package com.tabac.ebsintegratortestapp.screens.favorites

import com.tabac.ebsintegratortestapp.BaseViewModel
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val productsRepository: ProductsRepository
) : BaseViewModel() {

    val favoritesData = productsRepository.getAllFavoriteProducts()

    fun removeFromFavorites(product: ProductDTO) {
        launch {
            productsRepository.removeFromFavorites(productId = product.id)
        }
    }
}