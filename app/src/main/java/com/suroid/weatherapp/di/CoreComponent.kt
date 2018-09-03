package com.suroid.weatherapp.di

import android.content.Context
import com.suroid.weatherapp.db.CityDao
import com.suroid.weatherapp.db.WeatherDb
import dagger.Component
import javax.inject.Singleton

/**
 * ViewModelInjector is a Component which injects core dependencies
 * @requires AppModule and RepositoryModule
 */
@Singleton
@Component(modules = [AppModule::class])
interface CoreComponent {

    fun context(): Context

    fun weatherDb(): WeatherDb

    fun cityDao(): CityDao
}