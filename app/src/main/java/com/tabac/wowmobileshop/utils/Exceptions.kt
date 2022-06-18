package com.tabac.wowmobileshop.utils

import com.tabac.wowmobileshop.data.network.ServerErrorDetail

open class ServerException(serverError: ServerErrorDetail) : Throwable(message = serverError.detail)