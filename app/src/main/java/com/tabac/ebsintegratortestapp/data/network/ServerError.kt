package com.tabac.ebsintegratortestapp.data.network

import com.tabac.ebsintegratortestapp.utils.ErrorName
import java.io.Serializable

data class ServerError(
    var code: Int?,
    val message: String?,
    val error: ErrorName?,
) : Serializable

data class ServerErrorDetail(
    val detail: String
)
