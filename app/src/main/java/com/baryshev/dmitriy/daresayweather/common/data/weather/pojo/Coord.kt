package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lon") val lon: Double?,
    @SerializedName("lat") val lat: Double?
                )