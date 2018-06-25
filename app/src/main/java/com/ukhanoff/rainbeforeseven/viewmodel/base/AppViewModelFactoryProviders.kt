package com.ukhanoff.rainbeforeseven.viewmodel.base

import android.arch.lifecycle.ViewModel
import javax.inject.Provider

typealias AppViewModelFactoryProviders = Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
