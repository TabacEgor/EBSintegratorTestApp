package com.tabac.wowmobileshop.utils

import com.tabac.wowmobileshop.data.network.ServerErrorDetail

open class ServerException(val serverError: ServerErrorDetail) : Throwable()