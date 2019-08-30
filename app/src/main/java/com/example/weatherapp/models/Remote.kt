package com.example.weatherapp.models

data class Coordinate(val lat: Double, val lon:Double)

data class WeatherExtraInfo(val id: Int, val main: String, val description: String, val icon: String)

data class WeatherInfo(val temp: Double, val pressure: Int, val humidity: Int, val temp_min: Double, val temp_max: Double)

data class Wind(val speed: Int, val degree: Int)

data class Cloud(val all: Int)

data class Sys(val country:String, val sunrise: Long, val sunset: Long)

data class WeatherData(val coord: Coordinate, val weather: Array<WeatherExtraInfo>, val base: String, val sys: Sys, val main: WeatherInfo, val dt: Long, val timezone: Long, val id: Long, val name: String)