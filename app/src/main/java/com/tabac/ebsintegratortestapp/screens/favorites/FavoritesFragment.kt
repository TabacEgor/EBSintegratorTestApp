package com.tabac.ebsintegratortestapp.screens.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tabac.ebsintegratortestapp.BaseFragment
import com.tabac.ebsintegratortestapp.R
import com.tabac.ebsintegratortestapp.databinding.FragmentFavoritesBinding
import com.tabac.ebsintegratortestapp.model.domain.Product
import com.tabac.ebsintegratortestapp.screens.products.ProductListAdapter
import com.tabac.ebsintegratortestapp.utils.PRODUCT_ID
import com.tabac.ebsintegratortestapp.utils.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding, FavoritesViewModel>() {
    override val viewModel: FavoritesViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate
    override val render: FavoritesViewModel.() -> Unit = {
        favoritesData observe { showFavoriteProducts(it) }
        addToCartData observe { binding.btnCart.tvCartItems.text = it.toString() }
    }
    private val favoriteProductsAdapter: ProductListAdapter by lazy { ProductListAdapter(
        { onProductClick(it) },
        { product, _ -> onFavoriteClick(product) },
        { onAddToCartClick(it) } )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSupportActionBar(binding.toolBar)
        showBackButton()
        setupProductListRecyclerView()
    }

    private fun setupProductListRecyclerView() {
        with(binding.rvFavoriteProductList) {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            adapter = favoriteProductsAdapter
        }
    }

    private fun showFavoriteProducts(favoriteList: List<Product>) {
        favoriteProductsAdapter.submitList(favoriteList)
    }

    private fun onProductClick(product: Product) {
        findNavController().navigate(R.id.productFragment, bundleOf(PRODUCT_ID to product.id))
    }

    private fun onFavoriteClick(product: Product) {
        viewModel.removeFromFavorites(product)
    }

    private fun onAddToCartClick(product: Product) {
        viewModel.addToCart(product)
    }

    override fun clicks() {
        binding.btnFavorites onClick  {
            findNavController().navigate(
                FavoritesFragmentDirections.actionFavoritesFragmentToProductListFragment()
            )
        }
    }

}