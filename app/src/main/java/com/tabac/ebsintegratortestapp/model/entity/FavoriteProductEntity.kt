package com.tabac.ebsintegratortestapp.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tabac.ebsintegratortestapp.model.Category
import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO

@Entity(tableName = "favorite_products")
data class FavoriteProductEntity(
    @PrimaryKey
    val id : Int,
    val name : String,
    val details : String,
    val price : Int,
    val main_image : String
)

fun FavoriteProductEntity.toProduct(): Product {
    return Product(
        this.id,
        this.name,
        this.details,
        this.price,
        this.main_image,
        true
    )
}
