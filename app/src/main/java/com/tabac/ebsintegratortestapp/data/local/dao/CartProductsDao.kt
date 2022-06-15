package com.tabac.ebsintegratortestapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tabac.ebsintegratortestapp.model.entity.CartProductEntity
import com.tabac.ebsintegratortestapp.model.entity.FavoriteProductEntity

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