package com.tabac.ebsintegratortestapp.utils

import com.google.gson.Gson
import com.tabac.ebsintegratortestapp.data.network.ServerError
import com.tabac.ebsintegratortestapp.data.network.ServerErrorDetail
import retrofit2.Response

fun <T> Response<T>.processResponse(): T {
    val body = body()
    val gson = Gson()
    val errorBody = errorBody()
    if (isSuccessful) {
        if (body != null) return body
    } else if (errorBody != null) {
        try {
//            error model based on backend
//            val serverError = gson.fromJson(errorBody.string(), ServerError::class.java)
            val serverError = gson.fromJson(errorBody.string(), ServerErrorDetail::class.java)
            throw when (code()) {
                400, 404 -> ServerException(serverError)
                else -> ServerException(serverError)
            }
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
    throw toServerErrorDetail(message = "Api unexpected error, report it to api service administrator").toException()
}

private fun ServerErrorDetail.toException(): Throwable {
    return ServerException(this)
}

private fun <T> Response<T>.toServerErrorDetail(
    message: String = message(),
) = ServerErrorDetail(
    detail = message
)

private fun <T> Response<T>.toServerError(
    message: String = message(),
    code: Int = code(),
) = ServerError(
    code = code,
    error = ErrorName.SERVER_ERROR,
    message = message,
)