package com.suroid.weatherapp.di

import com.suroid.weatherapp.ui.cityselection.CitySelectionActivity
import com.suroid.weatherapp.ui.home.HomeActivity
import com.suroid.weatherapp.ui.weathercards.WeatherCardFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideHomeActivity(): HomeActivity

    @ContributesAndroidInjector(modules = [FragmentModule::class])
    abstract fun provideCitySelectionActivity(): CitySelectionActivity
}

@Suppress("unused")
@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun provideWeatherCardFragment(): WeatherCardFragment

}
