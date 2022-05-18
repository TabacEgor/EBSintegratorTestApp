package com.tabac.ebsintegratortestapp.model.responses

import com.tabac.ebsintegratortestapp.model.dto.ProductDTO

data class ProductsResponse(
    val count : Int,
    val next : String,
    val previous : String,
    val results : List<ProductDTO>
)
