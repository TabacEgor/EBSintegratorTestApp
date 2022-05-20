package com.tabac.ebsintegratortestapp.repository

import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.model.dto.toProductEntity
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val productsLocalDataSource: ProductsLocalDataSource
) {
    // TODO map with favorites
    suspend fun getProducts(pageNumber: Int) = productsRemoteDataSource.getProducts(pageNumber)

    suspend fun getProductDetails(productId: Int) = productsRemoteDataSource.getProductDetails(productId)

    suspend fun addToFavorites(product: ProductDTO) = productsLocalDataSource.addToFavorites(product.toProductEntity())

    suspend fun removeFromFavorites(productId: Int) = productsLocalDataSource.removeFromFavorites(productId)

    fun getAllFavoriteProducts() = productsLocalDataSource.getAllFavoritesProducts()
}