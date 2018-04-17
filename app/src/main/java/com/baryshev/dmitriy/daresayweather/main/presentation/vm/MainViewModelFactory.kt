package com.baryshev.dmitriy.daresayweather.main.presentation.vm

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.baryshev.dmitriy.daresayweather.common.domain.IResourceInteractor
import com.baryshev.dmitriy.daresayweather.main.di.MainScope
import com.baryshev.dmitriy.daresayweather.main.domain.IMainInteractor
import com.baryshev.dmitriy.daresayweather.utils.rx.IRxScheduler
import javax.inject.Inject

@MainScope
class MainViewModelFactory @Inject constructor(private val rxScheduler: IRxScheduler,
                                               private val mainInteractor: IMainInteractor,
                                               private val resourceInteractor: IResourceInteractor
                                              ) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainVM::class.java)) {
            @Suppress("UNCHECKED_CAST") return MainVM(
                rxScheduler,
                mainInteractor,
                resourceInteractor
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}