package com.baryshev.dmitriy.daresayweather.common.presentation

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * 4/12/2018.
 */
open class BaseVM : ViewModel() {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCleared() {
        compositeDisposable.clear()
    }

    protected inline fun addDisposable(code: () -> Disposable) {
        compositeDisposable.add(code())
    }
}