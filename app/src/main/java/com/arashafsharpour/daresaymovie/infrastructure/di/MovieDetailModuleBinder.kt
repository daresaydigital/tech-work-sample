package com.arashafsharpour.daresaymovie.infrastructure.di

import com.arashafsharpour.daresaymovie.features.moviedetail.infrastracture.IMovieDetailCoordinator
import com.arashafsharpour.daresaymovie.features.moviedetail.infrastracture.MovieDetailCoordinator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview

@FlowPreview
@InstallIn(SingletonComponent::class)
@Module
interface MovieDetailModuleBinder {
    @Binds
    fun bindMainCoordinator(
        splashCoordinator: MovieDetailCoordinator
    ): IMovieDetailCoordinator
}