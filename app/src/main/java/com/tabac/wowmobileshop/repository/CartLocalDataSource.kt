package com.tabac.wowmobileshop.repository

import androidx.lifecycle.liveData
import com.tabac.wowmobileshop.data.LocalDataSource
import com.tabac.wowmobileshop.data.local.dao.CartProductsDao
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.model.domain.toCartProductEntity
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