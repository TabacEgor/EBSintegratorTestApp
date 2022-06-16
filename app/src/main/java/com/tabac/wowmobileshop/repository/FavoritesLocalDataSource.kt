package com.tabac.wowmobileshop.repository

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tabac.wowmobileshop.data.LocalDataSource
import com.tabac.wowmobileshop.data.local.dao.FavoriteProductsDao
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.model.entity.FavoriteProductEntity
import com.tabac.wowmobileshop.model.entity.toProduct
import javax.inject.Inject

class FavoritesLocalDataSource @Inject constructor(
    private val favoriteProductsDao: FavoriteProductsDao
) : LocalDataSource() {

    suspend fun addToFavorites(productEntity: FavoriteProductEntity) = exchangeLocal {
        favoriteProductsDao.insert(productEntity)
    }

    suspend fun removeFromFavorites(productId: Int) = exchangeLocal {
        favoriteProductsDao.delete(productId)
    }

    suspend fun getAllFavoritesProductsFromDb() = exchangeLocal {
        favoriteProductsDao.getAllFavoriteProductsFromDb().map { it.toProduct() }
    }

    fun getAllFavoritesProducts() = liveData<List<Product>> {
        emitSource(favoriteProductsDao.getAllFavoriteProducts().map {
            val mappedFavoriteProduct = mutableListOf<Product>()
            it.forEach {
                mappedFavoriteProduct.add(it.toProduct())
            }
            mappedFavoriteProduct
        })
    }
}