package com.tabac.ebsintegratortestapp.model.domain

import com.tabac.ebsintegratortestapp.model.entity.CartProductEntity
import com.tabac.ebsintegratortestapp.model.entity.FavoriteProductEntity

data class Product(
    val id : Int,
    val name : String,
    val details : String,
    val price : Int,
    val main_image: String,
    var isFavorite: Boolean = false
)

fun Product.toFavoriteProductEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        this.id,
        this.name,
        this.details,
        this.price,
        this.main_image,
    )
}

fun Product.toCartProductEntity(): CartProductEntity {
    return CartProductEntity(
        this.id,
        this.name,
        this.details,
        this.price,
        this.main_image,
    )
}