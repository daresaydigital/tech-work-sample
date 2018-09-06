package com.suroid.weatherapp.di

import android.content.Context
import com.suroid.weatherapp.WeatherApplication
import com.suroid.weatherapp.repo.CityRepository
import com.suroid.weatherapp.repo.CityWeatherRepository
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * ViewModelInjector is a Component which injects core dependencies
 * @requires [AppModule] and [RepositoryModule]
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, RepositoryModule::class, ActivityModule::class])
interface CoreComponent {

    fun context(): Context

    fun cityRepo(): CityRepository

    fun cityWeatherRepo(): CityWeatherRepository

    fun inject(app: WeatherApplication)
}