package com.arashafsharpour.daresaymovie.infrastructure.di

import com.arashafsharpour.daresaymovie.features.main.IMainCoordinator
import com.arashafsharpour.daresaymovie.features.main.MainCoordinator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@FlowPreview
@InstallIn(SingletonComponent::class)
@Module
interface MainModuleBinder {

    @Binds
    fun bindMainCoordinator(
        mainCoordinator: MainCoordinator
    ): IMainCoordinator

}