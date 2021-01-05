package com.payz.softpos.presentation.core.extension

import androidx.fragment.app.Fragment
import com.payz.softpos.presentation.SoftPosApp
import com.payz.softpos.presentation.core.viewmodel.ViewModelFactory

fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repository = (requireContext().applicationContext as SoftPosApp).repository
    return ViewModelFactory(repository)
}