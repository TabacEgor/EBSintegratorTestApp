package com.tabac.wowmobileshop.data.network

import com.tabac.wowmobileshop.model.dto.ProductDTO
import com.tabac.wowmobileshop.model.responses.ProductsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface ProductsService {

    @GET("/products")
    suspend fun getAllProducts(@QueryMap productsRequest: Map<String, Int>): Response<ProductsResponse>

    @GET("/products/{id}")
    suspend fun getProductDetails(@Path("id") productId: Int): Response<ProductDTO>

}