package com.daresaydigital.data.features.popular_movie.di

import com.daresaydigital.data.features.popular_movie.PopularMoviesRepositoryImpl
import com.daresaydigital.domain.features.popular_movie.repository.PopularMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

/**
 * this module will bind all domain layer abstractions to data
 * layer implementations.
 */
@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class PopularMoviesRepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindComicRepository(
        repositoryImpl: PopularMoviesRepositoryImpl
    ): PopularMoviesRepository
}