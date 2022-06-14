package com.tabac.ebsintegratortestapp.repository

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tabac.ebsintegratortestapp.data.LocalDataSource
import com.tabac.ebsintegratortestapp.data.local.FavoriteProductsDao
import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.model.entity.FavoriteProductEntity
import com.tabac.ebsintegratortestapp.model.entity.toProduct
import javax.inject.Inject

class ProductsLocalDataSource @Inject constructor(
    private val productsDao: FavoriteProductsDao
) : LocalDataSource() {

    suspend fun addToFavorites(productEntity: FavoriteProductEntity) = exchangeLocal {
        productsDao.insert(productEntity)
    }

    suspend fun removeFromFavorites(productId: Int) = exchangeLocal {
        productsDao.delete(productId)
    }

    suspend fun getAllFavoritesProductsFromDb() = exchangeLocal {
        productsDao.getAllFavoriteProductsFromDb().map { it.toProduct() }
    }

    fun getAllFavoritesProducts() = liveData<List<Product>> {
        emitSource(productsDao.getAllFavoriteProducts().map {
            val mappedFavoriteProduct = mutableListOf<Product>()
            it.forEach {
                mappedFavoriteProduct.add(it.toProduct())
            }
            mappedFavoriteProduct
        })
    }
}