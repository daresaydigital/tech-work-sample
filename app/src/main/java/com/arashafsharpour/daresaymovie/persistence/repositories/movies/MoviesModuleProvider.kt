package com.arashafsharpour.daresaymovie.persistence.repositories.movies

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MoviesModuleProvider {

    @Provides
    @Singleton
    fun provideConfigRepository(dataSource: MoviesRepository): IMoviesRepository = dataSource
}