package com.tabac.ebsintegratortestapp.repository

import androidx.lifecycle.liveData
import com.tabac.ebsintegratortestapp.data.LocalDataSource
import com.tabac.ebsintegratortestapp.data.local.dao.CartProductsDao
import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.model.domain.toCartProductEntity
import javax.inject.Inject

class CartLocalDataSource @Inject constructor(
    private val cartProductsDao: CartProductsDao
) : LocalDataSource() {

    suspend fun addToCart(product: Product) = exchangeLocal {
        cartProductsDao.insert(product.toCartProductEntity())
    }

    fun getCartProductsCount() = liveData<Int> {
        emitSource(cartProductsDao.getCartProductsCount())
    }

    suspend fun removeAllCartProducts() = exchangeLocal {
        cartProductsDao.deleteAllCartProducts()
    }
}