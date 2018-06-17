package com.ivy.weatherapp

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.ivy.weatherapp.di.getWeatherAppModules
import org.koin.android.ext.android.startKoin

class WeatherApp : Application() {
    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)
        super.onCreate()
        startKoin(this, getWeatherAppModules())
    }
}