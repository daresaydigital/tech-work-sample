package com.sneha.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class DailyForecast(

    @SerializedName("list")
    val forecastList: List<ForecastItem>

) {

    data class ForecastItem(
        @SerializedName("dt")
        val time: Long,

        @SerializedName("weather")
        val weather: List<Weather.WeatherData>,

        @SerializedName("temp")
        val temp: Temp
    )

    data class Temp(
        @SerializedName("day")
        val day: Double,

        @SerializedName("max")
        val max: Double,

        @SerializedName("min")
        val min: Double
    )
}