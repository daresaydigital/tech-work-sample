package com.vp.weatherapp.ui.main

import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView


interface MainContract {

    interface View : BaseView<Presenter> {
        fun showSelectedCities(selectedCities: List<CityWithForecast>)
    }

    interface Presenter : BasePresenter<View> {
        fun getSelectedCities()
    }
}