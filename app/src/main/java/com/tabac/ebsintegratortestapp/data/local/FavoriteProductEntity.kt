package com.tabac.ebsintegratortestapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tabac.ebsintegratortestapp.model.Category
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO

@Entity(tableName = "products")
data class FavoriteProductEntity(
    @PrimaryKey
    val id : Int,
    val name : String,
    val details : String,
    val size : String,
    val colour : String,
    val price : Int,
    val main_image : String
)

fun FavoriteProductEntity.toProductDTO(): ProductDTO {
    return ProductDTO(
        this.id,
        this.name,
        this.details,
        this.size,
        this.colour,
        this.price,
        this.main_image,
        true
    )
}
