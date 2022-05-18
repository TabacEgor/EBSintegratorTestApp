package com.tabac.ebsintegratortestapp.utils

import com.google.gson.Gson
import com.tabac.ebsintegratortestapp.ServerError
import retrofit2.Response

fun <T> Response<T>.processResponse(): T {
    val body = body()
    val gson = Gson()
    val errorBody = errorBody()
    if (isSuccessful) {
        if (body != null) return body
    } else if (errorBody != null) {
        try {
            val serverError = gson.fromJson(errorBody.string(), ServerError::class.java)
            throw when (code()) {
                400, 404, 422 -> {
                    when (serverError.error) {
                        else -> ServerException(serverError)
                    }
                }
                else -> ServerException(serverError)
            }
        } catch (throwable: Throwable) {
            throw throwable
        }
    }
    throw toServerError(message = "Api unexpected error, report it to api service administrator").toException()
}

private fun ServerError.toException(): Throwable {
    return ServerException(this)
}

private fun <T> Response<T>.toServerError(
    message: String = message(),
    code: Int = code(),
) = ServerError(
    code = code,
    error = ErrorName.SERVER_ERROR,
    message = message,
)