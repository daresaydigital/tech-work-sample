package com.baryshev.dmitriy.daresayweather.common.di

import com.baryshev.dmitriy.daresayweather.common.data.network.Api
import com.baryshev.dmitriy.daresayweather.common.data.weather.WeatherRepo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * 4/12/2018.
 */
@Module
class WeatherModule {
    @Singleton
    @Provides
    fun provideWeatherRepo(api: Api): WeatherRepo = WeatherRepo(api)
}