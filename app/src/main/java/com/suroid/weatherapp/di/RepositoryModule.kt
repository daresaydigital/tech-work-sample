package com.suroid.weatherapp.di

import com.suroid.weatherapp.db.CityDao
import com.suroid.weatherapp.repo.CityRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module which provides all Repository dependencies dependency
 * @requires [AppModule] dependency
 */

@Module(includes = [AppModule::class])
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
}