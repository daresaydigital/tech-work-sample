package com.ivy.weatherapp

import android.app.Application
import com.ivy.weatherapp.di.getWeatherAppModules
import org.koin.android.ext.android.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, getWeatherAppModules())
    }
}