package com.suroid.weatherapp.di

import com.suroid.weatherapp.api.WeatherApi
import com.suroid.weatherapp.db.CityDao
import com.suroid.weatherapp.db.CityWeatherDao
import com.suroid.weatherapp.repo.CityRepository
import com.suroid.weatherapp.repo.CityWeatherRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module which provides all Repository dependencies dependency
 * @requires [AppModule] dependency
 */

@Module(includes = [AppModule::class, NetworkModule::class])
class RepositoryModule {
    /**
     * Provides the [CityRepository] instance.
     * @param cityDao [CityDao] implementation to used with repo
     * @return [CityRepository] instance.
     */
    @Provides
    @Singleton
    fun providesCityRepository(cityDao: CityDao): CityRepository {
        return CityRepository(cityDao)
    }

    /**
     * Provides the [CityWeatherRepository] instance.
     * @param cityWeatherDao [CityWeatherDao] implementation to used with repo
     * @param api [WeatherApi] implementation to used with repo
     * @return [CityWeatherRepository] instance.
     */
    @Provides
    @Singleton
    fun providesCityWeatherRepository(cityWeatherDao: CityWeatherDao, api: WeatherApi): CityWeatherRepository {
        return CityWeatherRepository(cityWeatherDao, api)
    }
}