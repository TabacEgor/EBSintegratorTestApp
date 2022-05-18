package com.tabac.ebsintegratortestapp.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PageScrollListener(
    private val onLoadMore: () -> Unit,
    private val remainingItemsCount: Int = 1
) : RecyclerView.OnScrollListener() {
    private var isLoading = false
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val totalItemCount = recyclerView.layoutManager?.itemCount ?: 0
        val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        if (lastVisibleItemPosition == totalItemCount - remainingItemsCount && !isLoading && dy > 0) {
            isLoading = true
            onLoadMore()
        }
    }

    /**
     * Should be called when page loading is completed
     */
    fun stopLoading() {
        isLoading = false
    }
}