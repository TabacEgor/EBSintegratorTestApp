package com.tabac.ebsintegratortestapp.screens.products

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
import com.tabac.ebsintegratortestapp.databinding.FragmentProductListBinding
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.utils.PRODUCT_ID
import com.tabac.ebsintegratortestapp.utils.PageScrollListener
import com.tabac.ebsintegratortestapp.utils.onClick
import com.tabac.ebsintegratortestapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>() {

    private val viewModel: ProductListViewModel by viewModels()
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

        with(viewModel) {
            productsData observe { productListAdapter.submitList(it) }
            nextPageData observe {
                productListAdapter.notifyDataSetChanged()
                postScrollListener.stopLoading()
            }
            errorData observeOnce { showToast(it) }
            successData observeOnce {
                productListAdapter.favoriteOrUnFavoriteItem(it)
            }
        }

        binding.btnFavorites onClick  {
            findNavController().navigate(
                ProductListFragmentDirections.actionNavigationProductListToNavigationFavorites()
            )
        }
    }

    private fun setupProductListRecyclerView() {
        with(binding.rvProductList) {
            layoutManager = LinearLayoutManager(requireContext())
            itemAnimator = null
            adapter = productListAdapter
            addOnScrollListener(postScrollListener)
        }
    }

    private fun onProductClick(product: ProductDTO) {
        findNavController().navigate(R.id.productFragment, bundleOf(PRODUCT_ID to product.id))
    }

    private fun onFavoriteClick(product: ProductDTO, position: Int) {
        viewModel.addProductToFavorite(product, position)
    }

    private fun onAddToCartClick(product: ProductDTO) {

    }

    override fun clicks() {
        binding.btnFavorites onClick  {
            findNavController().navigate(
                ProductListFragmentDirections.actionNavigationProductListToNavigationFavorites()
            )
        }
    }
}