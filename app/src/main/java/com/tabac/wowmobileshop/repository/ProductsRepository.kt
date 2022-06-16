package com.tabac.wowmobileshop.repository

import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.model.domain.toFavoriteProductEntity
import com.tabac.wowmobileshop.model.dto.toProduct
import javax.inject.Inject

class ProductsRepository @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val favoritesLocalDataSource: FavoritesLocalDataSource,
    private val cartLocalDataSource: CartLocalDataSource
) {
    suspend fun getProducts(pageNumber: Int): Result<List<Product>> {
        val products = productsRemoteDataSource.getProducts(pageNumber).getOrDefault(emptyList()).map {
                it.toProduct()
            }.also { products ->
                favoritesLocalDataSource.getAllFavoritesProductsFromDb().getOrDefault(emptyList())
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
        favoritesLocalDataSource.addToFavorites(product.toFavoriteProductEntity())

    suspend fun addToCart(product: Product) =
        cartLocalDataSource.addToCart(product)

    suspend fun removeFromFavorites(productId: Int) =
        favoritesLocalDataSource.removeFromFavorites(productId)
    suspend fun removeAllCartProducts() =
        cartLocalDataSource.removeAllCartProducts()

    fun getAllFavoriteProducts() = favoritesLocalDataSource.getAllFavoritesProducts()

    fun getCartProductCount() = cartLocalDataSource.getCartProductsCount()

}