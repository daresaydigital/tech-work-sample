package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName

/**
 * 4/12/2018.
 */
data class DailyWeather(
    @SerializedName("dt") val dt: Long?,
    @SerializedName("pressure") val pressure: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("weather") val weather: List<Weather>?,
    @SerializedName("temp") val temp: Temp
                       )