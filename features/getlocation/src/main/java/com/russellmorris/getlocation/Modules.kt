package com.russellmorris.getlocation

import com.russellmorris.getlocation.ui.viewmodel.GetLocationViewModel
import com.russellmorris.location.LocationProvider
import com.russellmorris.location.LocationProviderImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        locationProviderModule
    )
}

val viewModelModule: Module = module {
    viewModel { GetLocationViewModel() }
}

val locationProviderModule: Module = module {
    single { LocationProviderImpl(context = get(), locationResultListener = get()) as LocationProvider }
}