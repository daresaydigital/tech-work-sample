package com.sneha.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.sneha.weatherapp.data.model.Forecast

/**
 * Daily forecast response object
 */
data class DailyForecastResponse(
    @SerializedName("cod")
    val cod: Int,

    @SerializedName("list")
    val forecastList: List<Forecast>
)