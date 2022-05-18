package com.tabac.ebsintegratortestapp.network

import com.tabac.ebsintegratortestapp.model.responses.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductsService {

    @GET("/products")
    suspend fun getAllProducts(@QueryMap productsRequest: Map<String, String>): Response<ProductsResponse>

    @GET("")
    suspend fun getProductDetails(@Path("id") orderId: String): ProductsResponse


}