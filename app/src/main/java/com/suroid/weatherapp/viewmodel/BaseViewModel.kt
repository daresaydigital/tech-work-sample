package com.suroid.weatherapp.viewmodel

import android.arch.lifecycle.ViewModel
import com.suroid.weatherapp.WeatherApplication
import com.suroid.weatherapp.di.DaggerViewModelInjector
import com.suroid.weatherapp.di.ViewModelInjector
import com.suroid.weatherapp.ui.cityselection.CitySelectionViewModel
import com.suroid.weatherapp.ui.home.HomeViewModel
import com.suroid.weatherapp.ui.weathercards.WeatherCardViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * BaseViewModel that auto Injects the required dependencies provided that corresponding
 * child ViewModel injection is added in ViewModelInjectors
 */
abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

//    private val injector: ViewModelInjector = DaggerViewModelInjector
//            .builder()
//            .coreComponent(WeatherApplication.coreComponent)
//            .build()
//
//    init {
//        inject()
//    }
//
//    /**
//     * Injects the required dependencies
//     */
//    private fun inject() {
//        when (this) {
//            is CitySelectionViewModel -> injector.inject(this)
//            is HomeViewModel -> injector.inject(this)
//            is WeatherCardViewModel -> injector.inject(this)
//        }
//    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}