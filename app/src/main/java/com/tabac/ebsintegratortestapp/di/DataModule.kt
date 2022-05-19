package com.tabac.ebsintegratortestapp.di

import android.content.Context
import androidx.room.Room
import com.tabac.ebsintegratortestapp.data.LocalDataSource
import com.tabac.ebsintegratortestapp.data.local.EbsDatabase
import com.tabac.ebsintegratortestapp.data.local.FavoriteProductsDao
import com.tabac.ebsintegratortestapp.data.network.RemoteDataSource
import com.tabac.ebsintegratortestapp.data.network.network.ProductsService
import com.tabac.ebsintegratortestapp.repository.ProductsLocalDataSource
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
    fun provideProductsDao(appDatabase: EbsDatabase): FavoriteProductsDao {
        return appDatabase.favoriteProductsDao()
    }

    @Provides
    @Singleton
    fun provideProductsRemoteDataSource(): RemoteDataSource<ProductsService> {
        return ProductsRemoteDataSource()
    }

    @Provides
    @Singleton
    fun provideProductsLocalDataSource(appDatabase: EbsDatabase): LocalDataSource {
        return ProductsLocalDataSource(appDatabase.favoriteProductsDao())
    }

    @Provides
    @Singleton
    fun provideProductsRepository(
        productLocalDataSource: ProductsLocalDataSource,
        productsRemoteDataSource: ProductsRemoteDataSource
    ): ProductsRepository {
        return ProductsRepository(productsRemoteDataSource, productLocalDataSource)
    }
}