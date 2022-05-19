package com.tabac.ebsintegratortestapp.data.network.network

import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class NetworkRequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request().newBuilder().apply {
            addHeader("Accept", "application/json")
            addHeader("Accept-Language", Locale.getDefault().toLanguageTag())
            // TODO add token here
        }.build())
    }
}