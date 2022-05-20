package com.tabac.ebsintegratortestapp.screens.products

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContentProviderCompat.requireContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tabac.ebsintegratortestapp.BaseAdapter
import com.tabac.ebsintegratortestapp.R
import com.tabac.ebsintegratortestapp.databinding.ItemProductBinding
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.utils.onClick

class ProductListAdapter(
    private val productClickListener: (ProductDTO) -> Unit,
    private val favoriteClickListener: (ProductDTO, Int) -> Unit,
    private val addToCartClickListener: (ProductDTO) -> Unit
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
                Glide.with(tvProductName)
                    .asBitmap()
                    .load(item.main_image)
                    .into(ivProductImage)
                if (item.isFavorite) {
                    btnAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    btnAddToFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                }
                root onClick {
                    productClickListener.invoke(item)
                }
                btnAddToFavorite onClick {
                    favoriteClickListener.invoke(item, adapterPosition)
                }
                btnAddToCart onClick {
                    addToCartClickListener.invoke(item)
                }
            }
        }
    }

    fun favoriteOrUnFavoriteItem(position: Int) {
        val item = getItem(position)
        item.isFavorite = !item.isFavorite
        notifyItemChanged(position)
    }

}