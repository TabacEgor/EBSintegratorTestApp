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
import com.tabac.ebsintegratortestapp.utils.PageScrollListener
import com.tabac.ebsintegratortestapp.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListFragment : BaseFragment<FragmentProductListBinding>() {

    private val viewModel: ProductsViewModel by viewModels()
    private val productListAdapter: ProductListAdapter by lazy { ProductListAdapter { onProductClick(it) } }
    private val postScrollListener = PageScrollListener({ viewModel.loadNextPage() })

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductListBinding
        get() = FragmentProductListBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupProductListRecyclerView()
        viewModel.getProducts()

        with(viewModel) {
            productsData observe { productListAdapter.submitList(it) }
            errorData observeOnce { showToast(it) }
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
        findNavController().navigate(R.id.productFragment, bundleOf("productId" to product.id))
    }
}