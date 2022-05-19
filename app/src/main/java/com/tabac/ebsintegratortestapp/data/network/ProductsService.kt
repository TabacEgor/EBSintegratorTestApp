package com.tabac.ebsintegratortestapp.data.network.network

import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.model.responses.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductsService {

    @GET("/products")
    suspend fun getAllProducts(@QueryMap productsRequest: Map<String, String>): Response<ProductsResponse>

    @GET("/products/{id}")
    suspend fun getProductDetails(@Path("id") productId: Int): Response<ProductDTO>

}