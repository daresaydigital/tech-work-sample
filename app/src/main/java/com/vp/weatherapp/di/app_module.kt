package com.vp.weatherapp.di

import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.data.WeatherRepositoryImpl
import org.koin.dsl.module.applicationContext


val weatherModule = applicationContext {



    bean { WeatherRepositoryImpl(get(), get()) as WeatherRepository }
}

val weatherApp = listOf(weatherModule)