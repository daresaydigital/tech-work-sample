package com.sneha.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastItem(
    @SerializedName("main")
    val main: Weather.MainData,

    @SerializedName("weather")
    val weather: List<Weather.WeatherData>,

    @SerializedName("dt")
    val dt: Long,

    @SerializedName("dt_txt")
    val dt_txt: String
)