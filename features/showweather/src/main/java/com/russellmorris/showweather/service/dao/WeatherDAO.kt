package com.russellmorris.showweather.service.dao

import com.russellmorris.showweather.domain.entity.WeatherEntity
import com.squareup.moshi.Json

data class WeatherDAO(
    @field:Json(name = "weather") val weatherList: List<Weather>,
    @field:Json(name = "main") val main: Main,
    @field:Json(name = "wind") val wind: Wind,
    @field:Json(name = "sys") val sys: Sys,
    @field:Json(name = "timezone") val timezone: Int,
    @field:Json(name = "name") val name: String
)

data class Weather(
    @field:Json(name = "id") val id: Int,
    @field:Json(name = "main") val main: String,
    @field:Json(name = "description") val description: String,
    @field:Json(name = "icon") val icon: String
)

data class Main(
    @field:Json(name = "temp") val temp: Double,
    @field:Json(name = "feels_like") val feelsLike: Double
)

data class Wind(
    @field:Json(name = "speed") val speed: Double,
    @field:Json(name = "deg") val deg: Int
)

data class Sys(
    @field:Json(name = "sunrise") val sunrise: Long,
    @field:Json(name = "sunset") val sunset: Long
)

fun WeatherDAO.mapToDomain(): WeatherEntity =
    WeatherEntity(
        weatherId = weatherList[0].id,
        weatherTitle = weatherList[0].main,
        weatherDescription = weatherList[0].description,
        weatherIcon = weatherList[0].icon,
        temp = main.temp,
        feelsLike = main.feelsLike,
        windSpeed = wind.speed,
        windDirection = wind.deg,
        sunrise = sys.sunrise,
        sunset = sys.sunset,
        timezone = timezone,
        city = name)

