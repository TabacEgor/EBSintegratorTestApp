package com.tabac.wowmobileshop.model.responses

import com.tabac.wowmobileshop.model.dto.ProductDTO

data class ProductsResponse(
    val count : Int,
    val next : String,
    val previous : String,
    val results : List<ProductDTO>
)
