package com.tabac.wowmobileshop.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tabac.wowmobileshop.data.local.dao.CartProductsDao
import com.tabac.wowmobileshop.data.local.dao.FavoriteProductsDao
import com.tabac.wowmobileshop.model.entity.CartProductEntity
import com.tabac.wowmobileshop.model.entity.FavoriteProductEntity

@Database(
    entities = [FavoriteProductEntity::class, CartProductEntity::class],
    version = 2
)
abstract class WowAppDatabase: RoomDatabase() {
    abstract fun favoriteProductsDao(): FavoriteProductsDao
    abstract fun cartProductsDao(): CartProductsDao
}