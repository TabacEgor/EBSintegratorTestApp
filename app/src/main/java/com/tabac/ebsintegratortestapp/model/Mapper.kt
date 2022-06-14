package com.tabac.ebsintegratortestapp.model

interface Mapper<T : Any, R : Any> {
    fun toModel(value: T): R
    fun fromModel(value: R): T
}