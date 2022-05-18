package com.tabac.ebsintegratortestapp.model.requests

import com.tabac.ebsintegratortestapp.model.Pagination

data class ProductsRequest(
    val ordering: String? = null,
    val search: String? = null,
    val categoryId: String? = null,
    val pagination: Pagination = Pagination()
)

val ProductsRequestQueryMap : Map<String, String> = mapOf(
    "page" to "1",
    "page_size" to "10"
)
