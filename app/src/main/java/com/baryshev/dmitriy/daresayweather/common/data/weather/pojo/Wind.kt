package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") val speed: Double?,
    @SerializedName("deg") val deg: Double?
               )