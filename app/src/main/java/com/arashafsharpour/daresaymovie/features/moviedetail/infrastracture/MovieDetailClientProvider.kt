package com.arashafsharpour.daresaymovie.features.moviedetail.infrastracture

import com.arashafsharpour.daresaymovie.persistence.repositories.moviedetail.IMovieDetailApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class MovieDetailClientProvider {
    @Singleton
    @Provides
    fun provideConfigClient(
        retrofit: Retrofit
    ): IMovieDetailApi {
        return retrofit.create(IMovieDetailApi::class.java)
    }
}