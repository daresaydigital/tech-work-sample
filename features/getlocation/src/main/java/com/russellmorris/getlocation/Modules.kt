package com.russellmorris.getlocation

import com.russellmorris.location.LocationProvider
import com.russellmorris.location.LocationProviderImpl
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        locationProviderModule
    )
}

val locationProviderModule: Module = module {
    single { LocationProviderImpl(context = get(), locationResultListener = get()) as LocationProvider }
}