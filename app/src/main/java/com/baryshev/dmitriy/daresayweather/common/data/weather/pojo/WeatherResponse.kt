package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo
import com.google.gson.annotations.SerializedName


/**
 * 4/12/2018.
 */

data class WeatherResponse(
		@SerializedName("coord") val coord: Coord?,
		@SerializedName("sys") val sys: Sys?,
		@SerializedName("weather") val weather: List<Weather>?,
		@SerializedName("main") val main: Main?,
		@SerializedName("wind") val wind: Wind?,
		@SerializedName("rain") val rain: Rain?,
		@SerializedName("clouds") val clouds: Clouds?,
		@SerializedName("dt") val dt: Int?,
		@SerializedName("id") val id: Int?,
		@SerializedName("name") val name: String?,
		@SerializedName("cod") val cod: Int?
                          )









