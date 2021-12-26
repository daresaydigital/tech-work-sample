package com.arashafsharpour.daresaymovie.persistence.repositories.moviedetail

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieDetailModuleBinder {
    @Provides
    @Singleton
    fun provideConfigRepository(dataSource: MovieDetailRepository) : IMovieDetailRepository = dataSource
}