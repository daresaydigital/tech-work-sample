package com.sneha.weatherapp.data.remote.response

import com.google.gson.annotations.SerializedName
import com.sneha.weatherapp.data.model.ForecastItem

/**
 * Today forecast response object
 */
data class TodayForecastResponse(

    @SerializedName("list")
    val forecastList: List<ForecastItem>
)