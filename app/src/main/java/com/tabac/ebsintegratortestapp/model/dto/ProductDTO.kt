package com.tabac.ebsintegratortestapp.model.dto

import com.tabac.ebsintegratortestapp.data.local.FavoriteProductEntity

data class ProductDTO(
    val id : Int,
    val name : String,
    val details : String,
    val size : String,
    val colour : String,
    val price : Int,
    val main_image : String,
)

fun ProductDTO.toProductEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        this.id,
        this.name,
        this.details,
        this.size,
        this.colour,
        this.price,
        this.main_image
    )
}
