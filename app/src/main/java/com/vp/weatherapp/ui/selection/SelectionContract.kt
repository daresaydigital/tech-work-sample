package com.vp.weatherapp.ui.selection

import com.vp.weatherapp.data.local.entity.CityEntity
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView


interface SelectionContract {

    interface View : BaseView<Presenter> {
        fun showSelectedCities(results: List<CityWithForecast>)
        fun onCityDeleted()
    }

    interface Presenter : BasePresenter<View> {
        fun getSelectedCities()
        fun deleteSelectedCity(city: CityWithForecast)
    }
}