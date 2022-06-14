package com.tabac.ebsintegratortestapp.repository

import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.model.domain.toProductEntity
import com.tabac.ebsintegratortestapp.model.dto.toProduct
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val productsLocalDataSource: ProductsLocalDataSource
) {
    suspend fun getProducts(pageNumber: Int): Result<List<Product>> {
        val products = productsRemoteDataSource.getProducts(pageNumber).getOrDefault(emptyList()).map {
                it.toProduct()
            }.also { products ->
                productsLocalDataSource.getAllFavoritesProductsFromDb().getOrDefault(emptyList())
                    .map { favoriteProduct ->
                        products.map { product ->
                            if (favoriteProduct.id == product.id) {
                                product.isFavorite = true
                            }
                        }
                    }
            }
        return Result.success(products)
    }

    suspend fun addToFavorites(product: Product) =
        productsLocalDataSource.addToFavorites(product.toProductEntity())

    suspend fun removeFromFavorites(productId: Int) =
        productsLocalDataSource.removeFromFavorites(productId)

    fun getAllFavoriteProducts() = productsLocalDataSource.getAllFavoritesProducts()
}