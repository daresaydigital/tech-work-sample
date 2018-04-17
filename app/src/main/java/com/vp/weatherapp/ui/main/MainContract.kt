package com.vp.weatherapp.ui.main

import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView


interface MainContract {

    interface View : BaseView<Presenter> {
        fun buildFragments(selectedCities: List<String>)
    }

    interface Presenter : BasePresenter<View> {
        fun getSelectedCities()
    }
}