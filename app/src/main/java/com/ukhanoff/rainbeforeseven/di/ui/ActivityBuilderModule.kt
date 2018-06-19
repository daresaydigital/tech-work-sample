package com.ukhanoff.rainbeforeseven.di.ui

import com.ukhanoff.rainbeforeseven.WeatherActivity
import com.ukhanoff.rainbeforeseven.di.util.ForActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilderModule {

    @ContributesAndroidInjector(modules = [
                ActivityWeatherModule::class,
                FragmentBuilderModule::class])
    @ForActivity
    fun contributeWeatherActivity(): WeatherActivity

}
