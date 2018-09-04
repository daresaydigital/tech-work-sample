package com.suroid.weatherapp.models

data class WeatherModel(
        var title: String? = null,
        var description: String? = null,
        var temperature: TemperatureModel? = null,
        var windSpeed: Int? = null
)