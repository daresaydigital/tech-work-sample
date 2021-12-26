package com.daresaydigital.data.features.popular_movie.di

import com.daresaydigital.data.features.popular_movie.remote.PopularMovieApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PopularMoviesNetworkModule {

    @Singleton
    @Provides
    fun provideMovieDetailsApiService(retrofit: Retrofit): PopularMovieApiService {
        return retrofit.create(PopularMovieApiService::class.java)
    }
    
}