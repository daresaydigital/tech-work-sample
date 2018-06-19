package com.ukhanoff.rainbeforeseven.di.ui

import android.content.Context
import com.ukhanoff.rainbeforeseven.WeatherActivity
import com.ukhanoff.rainbeforeseven.di.util.ForActivity
import dagger.Binds
import dagger.Module

@Module(includes = [ActivityWeatherBindingsModule::class])
object ActivityWeatherModule

@Module
interface ActivityWeatherBindingsModule {

    @Binds
    @ForActivity
    fun bindsContext(activity: WeatherActivity): Context

}
