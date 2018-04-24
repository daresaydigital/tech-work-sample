package com.vp.weatherapp.ui.main

import com.vp.weatherapp.data.local.entity.*
import com.vp.weatherapp.util.BasePresenter
import com.vp.weatherapp.util.BaseView


interface WeatherContract {

    interface View : BaseView<Presenter> {
        fun displayCurrentWeather(city: CityWithForecast)
        fun displayHourlyForecast(list: List<HourlyForecastEntity>)
        fun displayDailyForecast(list: List<DailyForecastEntity>)
    }

    interface Presenter : BasePresenter<View> {
        fun getCurrentWeather(cityId: Long)
        fun getHourlyForecast(cityId: Long)
        fun updateHourlyForecast(cityId: Long)
        fun getDailyForecast(cityId: Long)
        fun updateDailyForecast(cityId: Long)
    }
}