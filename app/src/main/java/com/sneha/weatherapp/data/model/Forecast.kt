package com.sneha.weatherapp.data.model

import com.google.gson.annotations.SerializedName

/**
 * Today forecast response object
 */
data class Forecast(

    @SerializedName("list")
    val weatherList: List<Item>
) {
    data class Item(
        @SerializedName("main")
        val main: Weather.MainData,

        @SerializedName("weather")
        val weather: List<Weather.WeatherData>,

        @SerializedName("dt")
        val dt: Long,

        @SerializedName("dt_txt")
        val dt_txt: String
    )
}