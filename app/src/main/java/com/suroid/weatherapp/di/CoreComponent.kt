package com.suroid.weatherapp.di

import android.content.Context
import com.suroid.weatherapp.repo.CityRepository
import dagger.Component
import javax.inject.Singleton

/**
 * ViewModelInjector is a Component which injects core dependencies
 * @requires [AppModule] and [RepositoryModule]
 */
@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class])
interface CoreComponent {

    fun context(): Context

    fun cityRepo(): CityRepository
}