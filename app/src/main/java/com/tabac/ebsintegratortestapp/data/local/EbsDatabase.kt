package com.tabac.ebsintegratortestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tabac.ebsintegratortestapp.model.entity.FavoriteProductEntity

@Database(
    entities = [FavoriteProductEntity::class],
    version = 2
)
abstract class EbsDatabase: RoomDatabase() {
    abstract fun favoriteProductsDao(): FavoriteProductsDao
}