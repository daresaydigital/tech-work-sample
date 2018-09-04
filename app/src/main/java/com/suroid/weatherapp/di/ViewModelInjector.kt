package com.suroid.weatherapp.di

import com.suroid.weatherapp.ui.cityselection.CitySelectionViewModel
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

}