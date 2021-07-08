package com.russellmorris.showweather.ui.model

import com.russellmorris.showweather.domain.entity.WeatherEntity

data class Weather(
    val weatherId: Int,
    val weatherTitle: String,
    val weatherDescription: String,
    val weatherIcon: String,
    val temp: Double,
    val feelsLike: Double,
    val windSpeed: Double,
    val windDirection: Int,
    val sunrise: Long,
    val sunset: Long,
    val timezone: Int,
    val city: String
)

fun WeatherEntity.mapToPresentation(): Weather =
    Weather(weatherId, weatherTitle, weatherDescription, weatherIcon, temp, feelsLike, windSpeed,
        windDirection, sunrise, sunset, timezone, city)
