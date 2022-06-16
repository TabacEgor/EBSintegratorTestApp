package com.tabac.wowmobileshop.screens.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tabac.wowmobileshop.BaseFragment
import com.tabac.wowmobileshop.R
import com.tabac.wowmobileshop.databinding.FragmentProductListBinding
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.utils.PRODUCT_ID
import com.tabac.wowmobileshop.utils.PageScrollListener
import com.tabac.wowmobileshop.utils.onClick
import com.tabac.wowmobileshop.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding, ProductListViewModel>() {

    override val viewModel: ProductListViewModel by viewModels()
    override val render: ProductListViewModel.() -> Unit = {
        productsData observe { productListAdapter.submitList(it) }
        nextPageData observe {
            productListAdapter.notifyDataSetChanged()
            postScrollListener.stopLoading()
        }
        errorData observeOnce { showToast(it) }
        successData observeOnce { productListAdapter.favoriteOrUnFavoriteItem(it) }
        cartProductsCountData observe { binding.btnCart.tvCartItems.text = it.toString() }
    }
    private val productListAdapter: ProductListAdapter by lazy { ProductListAdapter(
        { onProductClick(it) },
        { product, position -> onFavoriteClick(product, position) },
        { onAddToCartClick(it)} )
    }
    private val postScrollListener = PageScrollListener({ viewModel.loadNextPage() })
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductListBinding
        get() = FragmentProductListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideBackButton()
        setupProductListRecyclerView()
        viewModel.getProducts()
    }

    private fun setupProductListRecyclerView() {
        with(binding.rvProductList) {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            adapter = productListAdapter
            addOnScrollListener(postScrollListener)
        }
    }

    private fun onProductClick(product: Product) {
        findNavController().navigate(R.id.productFragment, bundleOf(PRODUCT_ID to product.id))
    }

    private fun onFavoriteClick(product: Product, position: Int) {
        if (product.isFavorite) {
            viewModel.removeFromFavorites(product, position)
        } else {
            viewModel.addProductToFavorite(product, position)
        }
    }

    private fun onAddToCartClick(product: Product) {
        viewModel.addToCart(product)
    }

    override fun clicks() {
        with(binding) {
            btnFavorites onClick  {
                findNavController().navigate(
                    ProductListFragmentDirections.actionNavigationProductListToNavigationFavorites()
                )
            }
            btnList onClick {
                changeProductsPresentation(ProductsPresentations.LIST)
            }
            btnGrid onClick {
                changeProductsPresentation(ProductsPresentations.GRID)
            }
            btnCart.btnMyCart onClick {
                viewModel.clearCart()
            }
        }
    }

    private fun changeProductsPresentation(presentation: ProductsPresentations) {
        when(presentation) {
            ProductsPresentations.LIST -> {
                with(binding) {
                    btnList.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primaryColor)
                    btnList.imageTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
                    btnGrid.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.greyColor)
                    btnGrid.imageTintList = ContextCompat.getColorStateList(requireContext(), R.color.primaryColor)
                    rvProductList.layoutManager = LinearLayoutManager(requireContext())
                }
            }
            ProductsPresentations.GRID -> {
                with(binding) {
                    btnList.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.greyColor)
                    btnList.imageTintList = ContextCompat.getColorStateList(requireContext(), R.color.primaryColor)
                    btnGrid.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primaryColor)
                    btnGrid.imageTintList = ContextCompat.getColorStateList(requireContext(), R.color.white)
                    rvProductList.layoutManager = GridLayoutManager(requireContext(), 2)
                }
            }
        }
    }
}