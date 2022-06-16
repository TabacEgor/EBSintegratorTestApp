package com.tabac.wowmobileshop.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

infix fun <V : View> V.onClick(block: V.() -> Unit) = setOnClickListener { block(it as V) }

fun Activity.showToast(message: String?) = showToastInternal(this, message)
fun Fragment.showToast(message: String?) = showToastInternal(requireActivity(), message)
private fun showToastInternal(context: Context, message: String?) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()