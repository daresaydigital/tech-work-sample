package com.daresaydigital.data.features.movie_details.di

import com.daresaydigital.data.features.movie_details.MovieDetailsRepositoryImpl
import com.daresaydigital.domain.features.movie_details.repository.MovieDetailsRepository
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
abstract class MovieDetailsRepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindMovieRepository(
        repositoryImpl: MovieDetailsRepositoryImpl
    ): MovieDetailsRepository
}