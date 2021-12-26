package com.arashafsharpour.daresaymovie.features.movies.infrastracture

import com.arashafsharpour.daresaymovie.persistence.repositories.movies.IMoviesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MoviesClientProvider {
    @Singleton
    @Provides
    fun provideConfigClient(
        retrofit: Retrofit
    ): IMoviesApi {
        return retrofit.create(IMoviesApi::class.java)
    }
}