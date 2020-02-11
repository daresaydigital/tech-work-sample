package com.sneha.weatherapp

import android.app.Application
import com.sneha.weatherapp.di.component.ApplicationComponent
import com.sneha.weatherapp.di.component.DaggerApplicationComponent
import com.sneha.weatherapp.di.module.ApplicationModule

class WeatherApplication : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        injectDependencies()
    }

    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }
}