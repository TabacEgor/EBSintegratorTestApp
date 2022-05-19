package com.tabac.ebsintegratortestapp.screens

import android.os.Bundle
import android.view.LayoutInflater
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.tabac.ebsintegratortestapp.BaseActivity
import com.tabac.ebsintegratortestapp.R
import com.tabac.ebsintegratortestapp.databinding.ActivityMainBinding
import com.tabac.ebsintegratortestapp.screens.products.ProductListFragmentDirections
import com.tabac.ebsintegratortestapp.utils.onClick
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = null
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.btnFavorites onClick {
            navController.navigate(
                ProductListFragmentDirections.actionNavigationProductListToNavigationFavorites()
            )
        }
    }
}