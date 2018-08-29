package com.example.gustens.darsey_arbetsprov_kotlin.di

import com.example.data.api.Api

import com.example.data.reopsitory.WeatherRepositoryImpl
import com.example.domain.WeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    @Singleton
    fun provideMovieRepository(api: Api): WeatherRepository {

        return WeatherRepositoryImpl(api)
    }

}