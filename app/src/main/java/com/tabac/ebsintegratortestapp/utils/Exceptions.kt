package com.tabac.ebsintegratortestapp.utils

import com.tabac.ebsintegratortestapp.data.network.ServerErrorDetail

open class ServerException(val serverError: ServerErrorDetail) : Throwable()