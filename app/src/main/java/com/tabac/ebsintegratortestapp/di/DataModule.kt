package com.tabac.ebsintegratortestapp.di

import android.content.Context
import androidx.room.Room
import com.tabac.ebsintegratortestapp.data.LocalDataSource
import com.tabac.ebsintegratortestapp.data.local.EbsDatabase
import com.tabac.ebsintegratortestapp.data.local.dao.FavoriteProductsDao
import com.tabac.ebsintegratortestapp.data.RemoteDataSource
import com.tabac.ebsintegratortestapp.data.local.dao.CartProductsDao
import com.tabac.ebsintegratortestapp.data.network.ProductsService
import com.tabac.ebsintegratortestapp.repository.CartLocalDataSource
import com.tabac.ebsintegratortestapp.repository.FavoritesLocalDataSource
import com.tabac.ebsintegratortestapp.repository.ProductsRemoteDataSource
import com.tabac.ebsintegratortestapp.repository.ProductsRepository
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
    fun provideRoomDatabaseInstance(@ApplicationContext appContext: Context): EbsDatabase {
        return Room.databaseBuilder(appContext, EbsDatabase::class.java, "ebs_database")
            .build()
    }

    @Provides
    @Singleton
    fun provideFavoritesProductsDao(appDatabase: EbsDatabase): FavoriteProductsDao {
        return appDatabase.favoriteProductsDao()
    }

    @Provides
    @Singleton
    fun provideCartProductsDao(appDatabase: EbsDatabase): CartProductsDao {
        return appDatabase.cartProductsDao()
    }

    @Provides
    @Singleton
    fun provideProductsRemoteDataSource(): RemoteDataSource<ProductsService> {
        return ProductsRemoteDataSource()
    }

    @Provides
    @Singleton
    fun provideFavoritesLocalDataSource(appDatabase: EbsDatabase): LocalDataSource {
        return FavoritesLocalDataSource(appDatabase.favoriteProductsDao())
    }

    @Provides
    @Singleton
    fun provideCartLocalDataSource(appDatabase: EbsDatabase): LocalDataSource {
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