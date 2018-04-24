package com.vp.weatherapp.data.local.entity


data class CityDailyForecast(
        var dt: Long   = 0,
        var icon: String = "",
        var temp_day: Double = 0.0,
        var temp_night: Double = 0.0
)