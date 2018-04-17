package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName

/**
 * 4/12/2018.
 */
data class Rain(
    @SerializedName("3h") val h: Double?
               )