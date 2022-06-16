package com.tabac.wowmobileshop.utils

import timber.log.Timber

fun logd(value: String) {
    Timber.d(value)
}
fun logv(value: String) {
    Timber.v(value)
}
fun loge(value: String, t: Throwable) {
    Timber.e(value, t)
}
object LoggerConstants {
    const val NETWORK_LOG = "NETWORK_LOG"
}