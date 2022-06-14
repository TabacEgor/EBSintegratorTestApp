package com.tabac.ebsintegratortestapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.tabac.ebsintegratortestapp.utils.Event
import com.tabac.ebsintegratortestapp.utils.Inflate

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment() {

    protected abstract val viewModel: VM

    /**
     * Handle view model observers
     */
    protected abstract val render: VM.() -> Unit

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding)
    abstract val bindingInflater: Inflate<VB>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.render()
        setupUI()
        clicks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun showBackButton() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    open fun hideBackButton() {
        (requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    fun setSupportActionBar(toolBar: androidx.appcompat.widget.Toolbar) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolBar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = null
    }

    open fun clicks() {}
    open fun setupUI() {}

    infix fun <T> LiveData<T>.observe(observer: (t: T) -> Unit) = observe(viewLifecycleOwner, Observer(observer))
    infix fun <T> LiveData<Event<T>>.observeOnce(observer: (t: T) -> Unit) = observe(viewLifecycleOwner) { it.getContentIfNotHandled()?.let(observer) }
}