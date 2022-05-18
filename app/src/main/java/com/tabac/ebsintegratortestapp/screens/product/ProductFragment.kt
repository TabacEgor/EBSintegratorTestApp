package com.tabac.ebsintegratortestapp.screens.product

import android.view.LayoutInflater
import android.view.ViewGroup
import com.tabac.ebsintegratortestapp.BaseFragment
import com.tabac.ebsintegratortestapp.databinding.FragmentProductBinding

class ProductFragment : BaseFragment<FragmentProductBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProductBinding
        get() = FragmentProductBinding::inflate
}