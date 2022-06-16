package com.tabac.wowmobileshop.utils

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class SimpleDiffUtil<T>(
    private var contentComparator: ((T, T) -> Boolean)? = null,
    private var itemsComparator: ((T, T) -> Boolean)? = null
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return itemsComparator?.invoke(oldItem, newItem) ?: oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return contentComparator?.invoke(oldItem, newItem) ?: oldItem == newItem
    }
}