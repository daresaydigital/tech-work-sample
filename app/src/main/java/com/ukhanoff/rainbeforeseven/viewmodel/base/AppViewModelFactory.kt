package com.ukhanoff.rainbeforeseven.viewmodel.base

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class AppViewModelFactory @Inject constructor(

    private val viewModelFactories: AppViewModelFactoryProviders

) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModelFactories[modelClass]?.get() as T

}
