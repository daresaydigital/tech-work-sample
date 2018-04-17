package com.baryshev.dmitriy.daresayweather.main.domain

import android.support.annotation.DrawableRes

sealed class MainData {

    data class CurrentWeather(val city: String,
                              val temp: String,
                              @DrawableRes val iconCondition: Int,
                              val condition: String,
                              val minTemp: String,
                              val maxTemp: String,
                              val wind: String,
                              val pressure: String,
                              val humidity: String,
                              val advice: String
    ) : MainData()

    data class Forecast(val date: String,
                        val day: String,
                        val minTemp: String,
                        val maxTemp: String,
                        @DrawableRes val iconCondition: Int)

}

