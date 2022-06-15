package com.tabac.ebsintegratortestapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tabac.ebsintegratortestapp.data.local.dao.CartProductsDao
import com.tabac.ebsintegratortestapp.data.local.dao.FavoriteProductsDao
import com.tabac.ebsintegratortestapp.model.entity.CartProductEntity
import com.tabac.ebsintegratortestapp.model.entity.FavoriteProductEntity

@Database(
    entities = [FavoriteProductEntity::class, CartProductEntity::class],
    version = 2
)
abstract class EbsDatabase: RoomDatabase() {
    abstract fun favoriteProductsDao(): FavoriteProductsDao
    abstract fun cartProductsDao(): CartProductsDao
}