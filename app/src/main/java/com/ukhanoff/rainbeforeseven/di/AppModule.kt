package com.ukhanoff.rainbeforeseven.di

import android.content.Context
import com.ukhanoff.rainbeforeseven.WeatherApplication
import dagger.Binds
import dagger.Module


@Module(
        includes = [
            AppModuleBindings::class
        ]
)

object AppModule {

}


@Module
interface AppModuleBindings {

    @Binds
    @ForApplication
    fun bindsContext(app: WeatherApplication): Context

}
