package com.tabac.wowmobileshop.repository

import com.tabac.wowmobileshop.model.dto.ProductDTO
import com.tabac.wowmobileshop.data.network.ProductsService
import com.tabac.wowmobileshop.data.RemoteDataSource
import com.tabac.wowmobileshop.model.Pagination
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.model.dto.toProduct
import javax.inject.Inject

const val PAGE_NUMBER = "page"
const val PAGE_SIZE = "page_size"

class ProductsRemoteDataSource @Inject constructor() : RemoteDataSource<ProductsService>(ProductsService::class) {
    private val pagination = Pagination()

    suspend fun getProducts(pageNumber: Int): Result<List<ProductDTO>> = exchange  {
        service.getAllProducts(mapOf(PAGE_NUMBER to pageNumber, PAGE_SIZE to pagination.page_size))
    }.mapCatching {
        it.results
    }

    suspend fun getProductDetails(productId: Int): Result<Product> = exchange {
        service.getProductDetails(productId)
    }.mapCatching {
        it.toProduct()
    }

}