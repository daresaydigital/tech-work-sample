package com.daresaydigital.data.features.movie_details.di

import com.daresaydigital.data.features.movie_details.remote.MovieDetailsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MovieDetailsNetworkModule {

    @Singleton
    @Provides
    fun provideMovieDetailsApiService(retrofit: Retrofit): MovieDetailsApiService {
        return retrofit.create(MovieDetailsApiService::class.java)
    }

}