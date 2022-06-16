package com.tabac.wowmobileshop

import androidx.lifecycle.*
import com.tabac.wowmobileshop.data.DataState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class BaseViewModel : ViewModel(), LifecycleEventObserver {
    val state by lazy { MutableLiveData<DataState<*>>() }

    fun <T> launch(coroutineContext: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> Result<T>): Job {
        return viewModelScope.launch(coroutineContext) {
            state.postValue(DataState.Progress())
            block()
                .onSuccess { state.postValue(DataState.Success(it)) }
                .onFailure { state.postValue(DataState.Fail<T>(it)) }
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
    }

    /**
     * Override it to handle refresh UI action. The method is triggered from SwipeRefreshLayout.
     */
    open fun onRefresh() {}
}