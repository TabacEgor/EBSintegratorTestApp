package com.tabac.ebsintegratortestapp.data

sealed class DataState<T>(val payload: T?, val throwable: Throwable?) {
    class Progress: DataState<Any>(null, null)
    class Success<T>(payload: T) : DataState<T>(payload, null)
    class Fail<T>(throwable: Throwable) : DataState<T>(null, throwable)
}