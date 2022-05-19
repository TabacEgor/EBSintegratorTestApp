package com.tabac.ebsintegratortestapp

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.tabac.ebsintegratortestapp.utils.Event

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = requireNotNull(_binding)
    abstract val bindingInflater: (LayoutInflater) -> VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    infix fun <T> LiveData<T>.observe(observer: (t: T) -> Unit) {
        observe(this@BaseActivity, Observer(observer))
    }

    infix fun <T> LiveData<Event<T>>.observeOnce(observer: (t: T) -> Unit) {
        observe(this@BaseActivity) { it.getContentIfNotHandled()?.let(observer) }
    }
}
