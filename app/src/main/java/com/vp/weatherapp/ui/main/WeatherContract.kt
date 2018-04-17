package com.vp.weatherapp.ui.main

import com.vp.weatherapp.data.local.entity.DailyForecastEntity
import com.vp.weatherapp.data.local.entity.HourlyForecastEntity
import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView


interface WeatherContract {

    interface View : BaseView<Presenter> {
        fun displayHourlyForecast(forecast: List<HourlyForecastEntity>)
        fun displayDailyForecast(forecast: List<DailyForecastEntity>)
    }

    interface Presenter : BasePresenter<View> {
        fun getHourlyForecast(city: String, country: String)
        fun getDailyForecast(city: String, country: String)
    }
}