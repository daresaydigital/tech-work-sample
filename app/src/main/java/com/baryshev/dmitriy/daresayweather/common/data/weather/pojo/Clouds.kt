package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName



data class Clouds(
    @SerializedName("all") val all: Int?
                 )