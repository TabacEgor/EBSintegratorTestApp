package com.tabac.wowmobileshop.data.network

import com.tabac.wowmobileshop.utils.ErrorName
import java.io.Serializable

data class ServerError(
    var code: Int?,
    val message: String?,
    val error: ErrorName?,
) : Serializable

data class ServerErrorDetail(
    val detail: String
): Serializable
