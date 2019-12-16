package com.russellmorris.showweather.domain.entity

data class WeatherEntity (
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