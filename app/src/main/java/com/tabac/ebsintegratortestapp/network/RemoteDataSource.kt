package com.tabac.ebsintegratortestapp.network

import com.tabac.ebsintegratortestapp.utils.processResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.reflect.KClass

abstract class RemoteDataSource<Service : Any>(clazz: KClass<Service>) {
    val service: Service by retrofitService(clazz.java)
    suspend fun <Model> exchange(block: suspend () -> Response<Model>): Result<Model> {
        return withContext(Dispatchers.IO) {
            runCatching {
                block().processResponse()
            }
        }
    }
}