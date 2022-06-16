package com.tabac.wowmobileshop.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_products")
data class CartProductEntity(
    @PrimaryKey
    val id : Int,
    val name : String,
    val details : String,
    val price : Int,
    val main_image : String
)