package com.arashafsharpour.daresaymovie.infrastructure.di

import com.arashafsharpour.daresaymovie.features.movies.infrastracture.IMoviesCoordinator
import com.arashafsharpour.daresaymovie.features.movies.infrastracture.MoviesCoordinator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@FlowPreview
@InstallIn(SingletonComponent::class)
@Module
interface MoviesModuleBinder {
    @Binds
    fun bindMainCoordinator(
        splashCoordinator: MoviesCoordinator
    ): IMoviesCoordinator
}