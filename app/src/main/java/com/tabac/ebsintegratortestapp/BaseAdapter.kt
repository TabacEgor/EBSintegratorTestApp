package com.tabac.ebsintegratortestapp

import android.view.View
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tabac.ebsintegratortestapp.utils.SimpleDiffUtil

abstract class BaseAdapter<Item: Any, VH : BaseAdapter.BaseViewHolder<Item>>(diffUtil: DiffUtil.ItemCallback<Item> = SimpleDiffUtil())
    : ListAdapter<Item, VH>(diffUtil) {
    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))
    abstract class BaseViewHolder<Item>(val view: View) : RecyclerView.ViewHolder(view) {
        abstract fun onBind(item: Item)
        var item: Item? = null
        fun bind(item: Item) {
            this.item = item
            onBind(item)
        }
    }
}