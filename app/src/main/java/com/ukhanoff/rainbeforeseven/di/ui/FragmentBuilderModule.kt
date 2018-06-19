package com.ukhanoff.rainbeforeseven.di.ui

import com.ukhanoff.rainbeforeseven.di.util.ForFragment
import com.ukhanoff.rainbeforeseven.fragment.WeatherFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilderModule {

    @ContributesAndroidInjector(modules = [FragmentWeatherModule::class])
    @ForFragment
    fun contributeWeatherFragment(): WeatherFragment

}
