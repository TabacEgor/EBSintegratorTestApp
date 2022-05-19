package com.tabac.ebsintegratortestapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteProductsDao {

    @Query("SELECT * from products")
    fun getAllFavoriteProducts(): LiveData<List<FavoriteProductEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: FavoriteProductEntity)
}