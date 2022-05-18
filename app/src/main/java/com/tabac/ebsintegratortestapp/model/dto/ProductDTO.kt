package com.tabac.ebsintegratortestapp.model.dto

import com.tabac.ebsintegratortestapp.model.Category

data class ProductDTO(
    val category : Category,
    val name : String,
    val details : String,
    val size : String,
    val colour : String,
    val price : Int,
    val main_image : String,
    val id : Int
)
