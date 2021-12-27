package com.daresaydigital.data.features.top_rated_movie.di

import com.daresaydigital.data.features.top_rated_movie.TopRatedMoviesRepositoryImpl
import com.daresaydigital.domain.features.top_rated_movie.repository.TopRatedMoviesRepository
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
abstract class TopRatedMoviesRepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindComicRepository(
        repositoryImpl: TopRatedMoviesRepositoryImpl
    ): TopRatedMoviesRepository
}