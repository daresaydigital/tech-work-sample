package com.suroid.weatherapp

import android.arch.lifecycle.ViewModel
import com.suroid.weatherapp.di.DaggerViewModelInjector
import com.suroid.weatherapp.di.ViewModelInjector
import com.suroid.weatherapp.ui.cityselection.CitySelectionViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * BaseViewModel that auto Injects the required dependencies provided that corresponding
 * child ViewModel injection is added in ViewModelInjectors
 */
abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    private val injector: ViewModelInjector = DaggerViewModelInjector
            .builder()
            .coreComponent(WeatherApplication.coreComponent)
            .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is CitySelectionViewModel -> injector.inject(this)
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}