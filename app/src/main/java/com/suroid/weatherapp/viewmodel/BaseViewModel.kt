package com.suroid.weatherapp.viewmodel

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * BaseViewModel that auto Injects the required dependencies provided that corresponding
 * child ViewModel injection is added in ViewModelInjectors
 */
abstract class BaseViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

}