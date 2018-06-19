package com.ukhanoff.rainbeforeseven.di

import android.content.Context
import com.ukhanoff.rainbeforeseven.WeatherApplication
import com.ukhanoff.rainbeforeseven.di.util.ForApplication
import dagger.Binds
import dagger.Module

@Module(includes = [AppModuleBindings::class])
object AppModule

@Module
interface AppModuleBindings {

    @Binds
    @ForApplication
    fun bindsContext(app: WeatherApplication): Context

}