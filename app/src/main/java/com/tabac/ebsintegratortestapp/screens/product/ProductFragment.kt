package com.tabac.ebsintegratortestapp.screens.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tabac.ebsintegratortestapp.BaseFragment
import com.tabac.ebsintegratortestapp.databinding.FragmentProductBinding
import com.tabac.ebsintegratortestapp.model.dto.ProductDTO
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductFragment : BaseFragment<FragmentProductBinding>() {

    private val viewModel: ProductViewModel by viewModels()
    private val args: ProductFragmentArgs by navArgs()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding
        get() = FragmentProductBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showBackButton()
        viewModel.getProductDetails(args.productId)

        with(viewModel) {
            productData observe {
                showProductDetails(it)
            }
        }
    }

    private fun showProductDetails(product: ProductDTO) {
        with(binding) {
            tvProductName.text = product.name
            tvProductShortDetails.text = product.details
            tvProductPrice.text = "$ ${product.price}" // TODO currency should come from server
            tvProductInfo.text = product.details
            // TODO add swipe to all product images
            Glide.with(requireActivity())
                .asBitmap()
                .load(product.main_image)
                .into(ivProductImage)
        }
    }

    override fun showBackButton() {
        super.showBackButton()
    }
}