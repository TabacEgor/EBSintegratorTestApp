package com.tabac.ebsintegratortestapp.repository

import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.data.network.network.ProductsService
import com.tabac.ebsintegratortestapp.data.network.RemoteDataSource
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor() : RemoteDataSource<ProductsService>(
    ProductsService::class) {

    suspend fun getProducts(): Result<List<ProductDTO>> = exchange  {
        service.getAllProducts(ProductsRequestQueryMap)
    }.mapCatching {
        it.results
    }

    suspend fun getProductDetails(productId: Int): Result<ProductDTO> = exchange {
        service.getProductDetails(productId)
    }.mapCatching {
        it
    }

    val ProductsRequestQueryMap : Map<String, String> = mapOf(
        "page" to "1",
        "page_size" to "10"
    )
}