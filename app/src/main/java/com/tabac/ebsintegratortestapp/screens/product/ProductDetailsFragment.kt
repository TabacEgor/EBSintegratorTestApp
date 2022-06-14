package com.tabac.ebsintegratortestapp.screens.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tabac.ebsintegratortestapp.BaseFragment
import com.tabac.ebsintegratortestapp.databinding.FragmentProductBinding
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import com.tabac.ebsintegratortestapp.screens.products.ProductListFragmentDirections
import com.tabac.ebsintegratortestapp.utils.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductBinding, ProductViewModel>() {

    override val viewModel: ProductViewModel by viewModels()
    override val render: ProductViewModel.() -> Unit = {
        productData observe { showProductDetails(it) }
    }
    private val args: ProductDetailsFragmentArgs by navArgs()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding
        get() = FragmentProductBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSupportActionBar(binding.toolBar)
        showBackButton()
        // TODO ViewPager for images
        viewModel.getProductDetails(args.productId)
    }

    @SuppressLint("SetTextI18n")
    private fun showProductDetails(product: ProductDTO) {
        with(binding) {
            tvProductName.text = product.name
            tvProductShortDetails.text = product.details
            tvProductPrice.text = "$ ${product.price},-" // TODO currency should come from server
            tvProductSecondPrice.text = "$ ${product.price},-"
            tvProductInfo.text = product.details
            // TODO add swipe to all product images
            Glide.with(requireActivity())
                .asBitmap()
                .load(product.main_image)
                .into(ivProductImage)
        }
    }

    override fun clicks() {
        binding.btnFavorites onClick  {
            findNavController().navigate(
                ProductListFragmentDirections.actionNavigationProductListToNavigationFavorites()
            )
        }
    }
}