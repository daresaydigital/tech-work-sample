package com.sneha.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Forecast(
    @SerializedName("dt")
    val dt: Long,

    @SerializedName("weather")
    val weather: List<Weather.WeatherData>,

    @SerializedName("temp")
    val temp: Temp,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("pressure")
    val pressure: Int,

    @SerializedName("sunrise")
    val sunrise: Long,

    @SerializedName("sunset")
    val sunset: Long
): Serializable