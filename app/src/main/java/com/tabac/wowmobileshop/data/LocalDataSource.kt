package com.tabac.wowmobileshop.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class LocalDataSource {
    suspend fun <Model> exchangeLocal(block: suspend () -> Model): Result<Model> {
        return withContext(Dispatchers.Default) {
            runCatching {
                block()
            }
        }
    }
}