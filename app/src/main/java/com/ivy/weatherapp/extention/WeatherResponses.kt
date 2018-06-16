package com.ivy.weatherapp.extention

import com.ivy.weatherapp.data.local.model.Weather
import com.ivy.weatherapp.data.remote.model.WeatherResponse

fun WeatherResponse.convert() = Weather(
        name = this.name,
        description = this.weather.firstOrNull()?.description ?: "",
        icon = this.weather.firstOrNull()?.icon ?: "",
        temp = this.main.temp,
        tempMin = this.main.tempMin.toInt(),
        tempMax = this.main.tempMax.toInt(),
        humidity = this.main.humidity,
        timestamp = this.timestamp
)