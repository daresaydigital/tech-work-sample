package com.baryshev.dmitriy.daresayweather.common.di

import android.content.Context
import com.baryshev.dmitriy.daresayweather.main.di.MainComponent
import com.baryshev.dmitriy.daresayweather.main.di.MainModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [AppModule::class, WeatherModule::class, GeoModule::class])
@Singleton
interface AppComponent {
    fun context(): Context

    fun plusMainComponent(module: MainModule): MainComponent
}