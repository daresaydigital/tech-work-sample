package com.daresaydigital.data.features.favorite_movie.di

import com.daresaydigital.data.features.favorite_movie.FavouriteMovieRepositoryImpl
import com.daresaydigital.domain.features.favourite_movie.repository.FavouriteMoviesRepository
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
abstract class FavouriteRepositoryModule {

    @Binds
    @ActivityRetainedScoped
    abstract fun bindComicRepository(
        repositoryImpl: FavouriteMovieRepositoryImpl
    ): FavouriteMoviesRepository
}