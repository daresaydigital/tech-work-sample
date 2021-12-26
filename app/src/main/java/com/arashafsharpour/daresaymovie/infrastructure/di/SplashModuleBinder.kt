package com.arashafsharpour.daresaymovie.infrastructure.di

import com.arashafsharpour.daresaymovie.features.splash.infrastructure.ISplashCoordinator
import com.arashafsharpour.daresaymovie.features.splash.infrastructure.SplashCoordinator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@FlowPreview
@InstallIn(SingletonComponent::class)
@Module
interface SplashModuleBinder {
    @Binds
    fun bindMainCoordinator(
        splashCoordinator: SplashCoordinator
    ): ISplashCoordinator
}