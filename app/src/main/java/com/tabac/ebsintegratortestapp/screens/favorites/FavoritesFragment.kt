package com.tabac.ebsintegratortestapp.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tabac.ebsintegratortestapp.BaseFragment
import com.tabac.ebsintegratortestapp.databinding.FragmentFavoritesBinding
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.screens.products.ProductListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val viewModel: FavoritesViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate
    private val favoriteProductsAdapter: ProductListAdapter by lazy { ProductListAdapter(
        { onProductClick(it) },
        { onFavoriteClick(it) },
        { onAddToCartClick(it) } )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductListRecyclerView()
        viewModel.favoritesData observe {
            showFavoriteProducts(it)
        }
    }

    private fun setupProductListRecyclerView() {
        with(binding.rvFavoriteProductList) {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            adapter = favoriteProductsAdapter
        }
    }

    private fun showFavoriteProducts(favoriteList: List<ProductDTO>) {
        favoriteProductsAdapter.submitList(favoriteList)
    }

    private fun onProductClick(product: ProductDTO) {
    }

    private fun onFavoriteClick(product: ProductDTO) {
        // TODO remove from favorite
    }

    private fun onAddToCartClick(product: ProductDTO) {

    }
}