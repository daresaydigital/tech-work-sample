package com.vp.weatherapp.ui.search

import com.vp.weatherapp.data.local.entity.CityEntity
import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView


interface SearchContract {

    interface View : BaseView<Presenter> {
        fun searchComplete(results: List<CityEntity>)
        fun onCitySelected(success: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun performSearch(text: String)
        fun saveSelectedCity(city: CityEntity)
    }
}