package com.tabac.ebsintegratortestapp.screens.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tabac.ebsintegratortestapp.BaseAdapter
import com.tabac.ebsintegratortestapp.databinding.ItemProductBinding
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.utils.onClick

class ProductListAdapter(
    private val productClickListener: (ProductDTO) -> Unit,
) : BaseAdapter<ProductDTO, BaseAdapter.BaseViewHolder<ProductDTO>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ProductDTO> {
        return ProductViewHolder(ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    inner class ProductViewHolder(val binding: ItemProductBinding) : BaseViewHolder<ProductDTO>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun onBind(item: ProductDTO) {
            with(binding) {
                tvProductName.text = item.name
                tvProductDetails.text = item.details
                tvProductPrice.text = "$ ${item.price}" // TODO Currency should comes from backend
                root onClick {
                    productClickListener.invoke(item)
                }
            }
        }

    }

}