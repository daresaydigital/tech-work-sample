package com.ivy.weatherapp.di

import android.arch.persistence.room.Room
import com.ivy.weatherapp.data.local.WeatherDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

fun getWeatherAppModules() = listOf(weatherModule)

val weatherModule = applicationContext {

    bean { Room.databaseBuilder(androidApplication(), WeatherDatabase::class.java, "weather_db").build() }
    bean { get<WeatherDatabase>().weatherRepository() }
}
