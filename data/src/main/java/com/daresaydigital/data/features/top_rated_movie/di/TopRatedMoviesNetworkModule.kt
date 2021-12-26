package com.daresaydigital.data.features.top_rated_movie.di

import com.daresaydigital.data.features.top_rated_movie.remote.TopRatedMoviesApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TopRatedMoviesNetworkModule {

    @Singleton
    @Provides
    fun provideMovieDetailsApiService(retrofit: Retrofit): TopRatedMoviesApiService {
        return retrofit.create(TopRatedMoviesApiService::class.java)
    }
    
}