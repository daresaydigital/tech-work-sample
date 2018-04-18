package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName

/**
 * 4/12/2018.
 */

data class ForecastResponse(@SerializedName("city") val city: City?,
                            @SerializedName("cod") val cod: String?,
                            @SerializedName("cnt") val cnt: Int?,
                            @SerializedName("list") val dailyWeatherData: List<DailyWeather>?
                           )












