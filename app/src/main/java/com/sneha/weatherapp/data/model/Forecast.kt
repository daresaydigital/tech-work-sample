package com.sneha.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("dt")
    val time: Long,

    @SerializedName("weather")
    val weather: List<Weather.WeatherData>,

    @SerializedName("main")
    val main: Weather.MainData

)