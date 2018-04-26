package com.vp.weatherapp.ui.initial

import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView


interface InitialContract {

    interface View : BaseView<Presenter> {
        fun onProgress(percent: Int)
        fun onComplete()
    }

    interface Presenter : BasePresenter<View> {
        fun initializeDatabase()
    }
}