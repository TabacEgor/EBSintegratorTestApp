package com.tabac.ebsintegratortestapp.di

import com.tabac.ebsintegratortestapp.network.ProductsService
import com.tabac.ebsintegratortestapp.network.RemoteDataSource
import com.tabac.ebsintegratortestapp.repository.ProductsRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataSourceModule {

    @Provides
    fun provideProductsDataSource(): RemoteDataSource<ProductsService> {
        return ProductsRemoteDataSource()
    }
}