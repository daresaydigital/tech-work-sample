package com.vp.weatherapp.data.remote

import com.google.gson.annotations.SerializedName


data class WeatherResponse(val coord: Coordinates, val weather: List<Weather>)

data class HourlyForecastResponse(val city: City, val cnt: Int, val list:List<HourlyForecast>)

data class HourlyForecast(val dt: Long, val main: HourlyMain, val weather: List<Weather>,
                          val clouds: Clouds, val wind: Wind, val rain: Rain)

data class HourlyMain(val temp: Double, val temp_min: Double, val temp_max: Double,
                val pressure: Double, val sea_level: Double, val grnd_level: Double,
                val humidity: Int, val temp_kf: Double)

data class Clouds(val all: Int)

data class Wind(val speed: Double, val deg: Double)

data class Rain(@SerializedName("3h") val rain_3h: Double)


data class DailyForecastResponse(val city: City, val list: List<DailyForecast>)

data class DailyForecast(val dt: Long, val temp: Temperature, val pressure: Float,
                         val humidity: Int, val weather: List<Weather>,
                         val speed: Float, val deg: Int, val clouds: Int,
                         val rain: Float)

data class City(val id: Long, val name: String, val coord: Coordinates,
                val country: String, val population: Int)

data class Coordinates(val lon: Float, val lat: Float)


data class Temperature(val day: Double, val min: Double, val max: Double,
                       val night: Double, val eve: Double, val morn: Double)

data class Weather(val id: Long, val main: String, val description: String,
                   val icon: String)