package com.tabac.ebsintegratortestapp.model.dto

import com.tabac.ebsintegratortestapp.model.domain.Product

data class ProductDTO(
    val id : Int,
    val name : String,
    val details : String,
    val size : String,
    val colour : String,
    val price : Int,
    val main_image : String,
)

fun ProductDTO.toProduct(): Product {
    return Product(
        this.id,
        this.name,
        this.details,
        this.price,
        this.main_image,
    )
}