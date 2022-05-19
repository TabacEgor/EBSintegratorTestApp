package com.tabac.ebsintegratortestapp.utils

import com.tabac.ebsintegratortestapp.data.network.ServerError

open class ServerException(val serverError: ServerError) : Throwable()