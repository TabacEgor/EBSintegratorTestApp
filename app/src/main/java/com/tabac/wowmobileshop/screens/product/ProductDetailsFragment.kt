package com.tabac.wowmobileshop.screens.product

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.tabac.wowmobileshop.BaseFragment
import com.tabac.wowmobileshop.R
import com.tabac.wowmobileshop.databinding.FragmentProductBinding
import com.tabac.wowmobileshop.model.domain.Product
import com.tabac.wowmobileshop.screens.products.ProductListFragmentDirections
import com.tabac.wowmobileshop.utils.onClick
import com.tabac.wowmobileshop.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : BaseFragment<FragmentProductBinding, ProductViewModel>() {

    override val viewModel: ProductViewModel by viewModels()
    override val render: ProductViewModel.() -> Unit = {
        productData observe { showProductDetails(it) }
        addToCartData observe { showToast(getString(R.string.add_to_cart_success)) }
        errorData observeOnce { showToast(it) }
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
    private fun showProductDetails(product: Product) {
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
        with(binding) {
            btnAddToCart onClick {
                viewModel.addToCart()
            }
            btnFavorites onClick  {
                findNavController().navigate(
                    ProductListFragmentDirections.actionNavigationProductListToNavigationFavorites()
                )
            }
        }
    }
}