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
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.screens.products.ProductListAdapter
import com.tabac.ebsintegratortestapp.screens.products.ProductListFragmentDirections
import com.tabac.ebsintegratortestapp.utils.PRODUCT_ID
import com.tabac.ebsintegratortestapp.utils.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<FragmentFavoritesBinding>() {

    private val viewModel: FavoritesViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoritesBinding
        get() = FragmentFavoritesBinding::inflate
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
        with(viewModel) {
            favoritesData observe {
                showFavoriteProducts(it)
            }
            addToCartData observe {
                binding.btnCart.tvCartItems.text = it.toString()
            }
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
        findNavController().navigate(R.id.productFragment, bundleOf(PRODUCT_ID to product.id))
    }

    private fun onFavoriteClick(product: ProductDTO) {
        viewModel.removeFromFavorites(product)
    }

    private fun onAddToCartClick(product: ProductDTO) {
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