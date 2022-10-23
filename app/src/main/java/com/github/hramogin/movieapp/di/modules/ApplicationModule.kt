package com.github.hramogin.movieapp.di.modules

import com.github.hramogin.movieapp.presentation.base.ResourceProvider
import com.github.hramogin.movieapp.presentation.base.ResourceProviderImpl
import org.koin.dsl.module

val applicationModule = module {

    single<ResourceProvider> { ResourceProviderImpl(get()) }
}