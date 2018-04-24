package com.vp.weatherapp.data.local.entity


data class CityHourlyForecast(
        var dt: Long   = 0,
        var icon: String = "",
        var temp: Double = 0.0
)