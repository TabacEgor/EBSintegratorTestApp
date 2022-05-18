package com.tabac.ebsintegratortestapp.utils

import com.tabac.ebsintegratortestapp.ServerError

open class ServerException(val serverError: ServerError) : Throwable()