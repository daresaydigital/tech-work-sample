package com.suroid.weatherapp

import android.app.Application
import com.suroid.weatherapp.di.AppModule
import com.suroid.weatherapp.di.CoreComponent
import com.suroid.weatherapp.di.DaggerCoreComponent

class WeatherApplication : Application() {

    companion object {
        lateinit var coreComponent: CoreComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDI()

    }

    private fun initDI() {
        coreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }
}