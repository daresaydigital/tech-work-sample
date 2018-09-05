package com.suroid.weatherapp.di

import com.suroid.weatherapp.ui.cityselection.CitySelectionViewModel
import com.suroid.weatherapp.ui.home.HomeViewModel
import com.suroid.weatherapp.ui.weathercards.WeatherCardViewModel
import dagger.Component

/**
 * ViewModelInjector is a Component which injects required dependencies into the specified []ViewModels.
 * @requires CoreComponent Dependency
 */
@ViewModelScope
@Component(dependencies = [CoreComponent::class])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified CitySelectionViewModel.
     * @param citySelectionViewModel [CitySelectionViewModel] in which to inject the dependencies
     */
    fun inject(citySelectionViewModel: CitySelectionViewModel)

    /**
     * Injects required dependencies into the specified HomeViewModel.
     * @param homeViewModel [HomeViewModel] in which to inject the dependencies
     */
    fun inject(homeViewModel: HomeViewModel)

    /**
     * Injects required dependencies into the specified WeatherCardViewModel.
     * @param weatherCardViewModel [WeatherCardViewModel] in which to inject the dependencies
     */
    fun inject(weatherCardViewModel: WeatherCardViewModel)

}