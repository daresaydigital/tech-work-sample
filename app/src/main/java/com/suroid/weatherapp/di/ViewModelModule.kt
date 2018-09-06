package com.suroid.weatherapp.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.suroid.weatherapp.ui.cityselection.CitySelectionViewModel
import com.suroid.weatherapp.ui.home.HomeViewModel
import com.suroid.weatherapp.ui.weathercards.WeatherCardViewModel
import com.suroid.weatherapp.viewmodel.WeatherViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CitySelectionViewModel::class)
    abstract fun bindCitySelectionViewModel(citySelectionViewModel: CitySelectionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WeatherCardViewModel::class)
    abstract fun bindWeatherCardViewModel(weatherCardViewModel: WeatherCardViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: WeatherViewModelFactory): ViewModelProvider.Factory
}
