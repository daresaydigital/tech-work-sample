package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName


/**
 * 4/13/2018.
 */

data class Temp(
    @SerializedName("day") val day: Double?,
    @SerializedName("min") val min: Double?,
    @SerializedName("max") val max: Double?,
    @SerializedName("night") val night: Double?,
    @SerializedName("eve") val eve: Double?,
    @SerializedName("morn") val morn: Double?
)