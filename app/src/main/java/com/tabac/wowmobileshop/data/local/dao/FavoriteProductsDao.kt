package com.tabac.wowmobileshop.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tabac.wowmobileshop.model.entity.FavoriteProductEntity

@Dao
interface FavoriteProductsDao {

    @Query("SELECT * from favorite_products")
    fun getAllFavoriteProducts(): LiveData<List<FavoriteProductEntity>>

    @Query("SELECT * from favorite_products")
    fun getAllFavoriteProductsFromDb(): List<FavoriteProductEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: FavoriteProductEntity)

    @Query("DELETE FROM favorite_products WHERE id = :productId")
    suspend fun delete(productId: Int)
}