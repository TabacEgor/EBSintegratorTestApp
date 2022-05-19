package com.tabac.ebsintegratortestapp.repository

import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.model.dto.toProductEntity
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val productsLocalDataSource: ProductsLocalDataSource
) {
    suspend fun getProducts() = productsRemoteDataSource.getProducts()

    suspend fun getProductDetails(productId: Int) = productsRemoteDataSource.getProductDetails(productId)

    suspend fun addToFavorites(product: ProductDTO) = productsLocalDataSource.addToFavorites(product.toProductEntity())

    fun getAllFavoriteProducts() = productsLocalDataSource.getAllFavoritesProducts()
}