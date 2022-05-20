package com.tabac.ebsintegratortestapp.repository

import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.tabac.ebsintegratortestapp.data.LocalDataSource
import com.tabac.ebsintegratortestapp.data.local.FavoriteProductEntity
import com.tabac.ebsintegratortestapp.data.local.FavoriteProductsDao
import com.tabac.ebsintegratortestapp.data.local.toProductDTO
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
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

    fun getAllFavoritesProducts() = liveData<List<ProductDTO>> {
        emitSource(productsDao.getAllFavoriteProducts().map {
            val mappedFavoriteProduct = mutableListOf<ProductDTO>()
            it.forEach {
                mappedFavoriteProduct.add(it.toProductDTO())
            }
            mappedFavoriteProduct
        })
    }
}