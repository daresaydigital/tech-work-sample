package com.example.malek.weatherapp.models


data class CurrentWeather(
        val coord: Coord?,
        val weather: List<Weather>?,
        val base: String?,
        val main: Main?,
        val wind: Wind?,
        val clouds: Clouds?,
        val dt: Int?,
        val sys: Sys?,
        val id: Int?,
        val name: String?,
        val cod: Int?,
        val message: String?
)