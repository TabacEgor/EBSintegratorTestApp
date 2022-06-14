package com.tabac.ebsintegratortestapp.model.domain

import com.tabac.ebsintegratortestapp.model.entity.FavoriteProductEntity

data class Product(
    val id : Int,
    val name : String,
    val details : String,
    val price : Int,
    val main_image: String,
    var isFavorite: Boolean = false
)

fun Product.toProductEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        this.id,
        this.name,
        this.details,
        this.price,
        this.main_image,
    )
}