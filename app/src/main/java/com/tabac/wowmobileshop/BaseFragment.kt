package com.tabac.wowmobileshop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewbinding.ViewBinding
import com.tabac.wowmobileshop.data.DataState
import com.tabac.wowmobileshop.utils.Event
import com.tabac.wowmobileshop.utils.Inflate
import com.tabac.wowmobileshop.utils.showToast

abstract class BaseFragment<VB : ViewBinding, VM : BaseViewModel> : Fragment(), SwipeRefreshLayout.OnRefreshListener {

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
        viewModel.state observe { state ->
            when(state) {
                is DataState.Success<*> -> hideProgress()
                is DataState.Fail<*> -> {
                    hideProgress()
                    onError(throwable = state.throwable ?: Throwable(getString(R.string.went_wrong)))
                }
                is DataState.Progress -> showProgress()
            }
        }
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
    open fun onError(throwable: Throwable) { showToast(throwable.message) }
    open fun hideProgress() {}
    open fun showProgress() {}
    override fun onRefresh() { viewModel.onRefresh() }

    infix fun <T> LiveData<T>.observe(observer: (t: T) -> Unit) = observe(viewLifecycleOwner, Observer(observer))
    infix fun <T> LiveData<Event<T>>.observeOnce(observer: (t: T) -> Unit) = observe(viewLifecycleOwner) { it.getContentIfNotHandled()?.let(observer) }
}