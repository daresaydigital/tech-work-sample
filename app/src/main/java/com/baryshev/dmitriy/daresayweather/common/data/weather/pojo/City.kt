package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName

/**
 * 4/12/2018.
 */
data class City(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("coord") val coord: Coord?,
    @SerializedName("country") val country: String?
)