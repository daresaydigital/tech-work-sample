package com.daresaydigital.data.di

import com.daresaydigital.data.features.popular_movie.remote.PopularMovieApiService
import com.daresaydigital.data.utils.FakeServer
import com.google.gson.Gson
import com.google.gson.GsonBuilder

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * this module will provide network related objects
 * mainly retrofit and it's dependencies
 */
@Module
@InstallIn(SingletonComponent::class)
class TestNetworkModule {

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideFakeServer(): FakeServer = FakeServer()

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(fakeServer: FakeServer, okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(fakeServer.baseEndpoint)
            .build()
    }

    @Singleton
    @Provides
    fun providePopularMoviesApiService(retrofit: Retrofit): PopularMovieApiService {
        return retrofit.create(PopularMovieApiService::class.java)
    }
}