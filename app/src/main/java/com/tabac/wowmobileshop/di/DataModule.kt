package com.tabac.wowmobileshop.di

import android.content.Context
import androidx.room.Room
import com.tabac.wowmobileshop.data.LocalDataSource
import com.tabac.wowmobileshop.data.local.WowAppDatabase
import com.tabac.wowmobileshop.data.local.dao.FavoriteProductsDao
import com.tabac.wowmobileshop.data.RemoteDataSource
import com.tabac.wowmobileshop.data.local.dao.CartProductsDao
import com.tabac.wowmobileshop.data.network.ProductsService
import com.tabac.wowmobileshop.repository.CartLocalDataSource
import com.tabac.wowmobileshop.repository.FavoritesLocalDataSource
import com.tabac.wowmobileshop.repository.ProductsRemoteDataSource
import com.tabac.wowmobileshop.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataModule {

    @Provides
    @Singleton
    fun provideRoomDatabaseInstance(@ApplicationContext appContext: Context): WowAppDatabase {
        return Room.databaseBuilder(appContext, WowAppDatabase::class.java, "ebs_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoritesProductsDao(appDatabase: WowAppDatabase): FavoriteProductsDao {
        return appDatabase.favoriteProductsDao()
    }

    @Provides
    @Singleton
    fun provideCartProductsDao(appDatabase: WowAppDatabase): CartProductsDao {
        return appDatabase.cartProductsDao()
    }

    @Provides
    @Singleton
    fun provideProductsRemoteDataSource(): RemoteDataSource<ProductsService> {
        return ProductsRemoteDataSource()
    }

    @Provides
    @Singleton
    fun provideFavoritesLocalDataSource(appDatabase: WowAppDatabase): LocalDataSource {
        return FavoritesLocalDataSource(appDatabase.favoriteProductsDao())
    }

    @Provides
    @Singleton
    fun provideCartLocalDataSource(appDatabase: WowAppDatabase): LocalDataSource {
        return CartLocalDataSource(appDatabase.cartProductsDao())
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        favoritesLocalDataSource: FavoritesLocalDataSource,
        cartLocalDataSource: CartLocalDataSource,
        productsRemoteDataSource: ProductsRemoteDataSource

    ): ProductsRepository {
        return ProductsRepository(productsRemoteDataSource, favoritesLocalDataSource, cartLocalDataSource)
    }
}