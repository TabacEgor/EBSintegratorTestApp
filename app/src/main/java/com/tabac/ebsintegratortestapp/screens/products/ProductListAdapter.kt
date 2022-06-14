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
import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.utils.onClick

class ProductListAdapter(
    private val productClickListener: (Product) -> Unit,
    private val favoriteClickListener: (Product, Int) -> Unit,
    private val addToCartClickListener: (Product) -> Unit
) : BaseAdapter<Product, BaseAdapter.BaseViewHolder<Product>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Product> {
        return ProductViewHolder(ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
        )
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : BaseViewHolder<Product>(binding.root) {
        @SuppressLint("SetTextI18n")
        override fun onBind(item: Product) {
            with(binding) {
                tvProductName.text = item.name
                tvProductDetails.text = item.details
                tvProductPrice.text = "$ ${item.price},-" // TODO Currency should comes from backend
                tvProductSecondPrice.text = "$ ${item.price},-"
                Glide.with(tvProductName)
                    .asBitmap()
                    .load(item.main_image)
                    .into(ivProductImage)
                if (item.isFavorite) {
                    btnAddToFavorite.setImageResource(R.drawable.ic_favorite)
                    btnAddToFavorite.backgroundTintList = binding.root.resources.getColorStateList(R.color.beigeColor, null)
                } else {
                    btnAddToFavorite.setImageResource(R.drawable.ic_favorite_border)
                    btnAddToFavorite.backgroundTintList = binding.root.resources.getColorStateList(R.color.white, null)
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