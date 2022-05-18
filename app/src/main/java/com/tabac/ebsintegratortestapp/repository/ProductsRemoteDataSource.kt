package com.tabac.ebsintegratortestapp.repository

import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.model.requests.ProductsRequest
import com.tabac.ebsintegratortestapp.network.ProductsService
import com.tabac.ebsintegratortestapp.network.RemoteDataSource
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor() : RemoteDataSource<ProductsService>(ProductsService::class) {

    suspend fun getProducts(): Result<List<ProductDTO>> = exchange  {
        service.getAllProducts(ProductsRequestQueryMap)
    }.mapCatching {
        it.results
    }

    val ProductsRequestQueryMap : Map<String, String> = mapOf(
        "page" to "1",
        "page_size" to "10"
    )
}