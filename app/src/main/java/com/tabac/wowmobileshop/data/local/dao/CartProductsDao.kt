package com.tabac.wowmobileshop.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tabac.wowmobileshop.model.entity.CartProductEntity

@Dao
interface CartProductsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(product: CartProductEntity)

    @Query("SELECT * from cart_products")
    fun getAllCartProducts(): LiveData<List<CartProductEntity>>

    @Query("SELECT COUNT(id) FROM cart_products")
    fun getCartProductsCount(): LiveData<Int>

    @Query("DELETE FROM cart_products")
    suspend fun deleteAllCartProducts()
}