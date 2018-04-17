package com.baryshev.dmitriy.daresayweather.common.data.weather.pojo

import com.google.gson.annotations.SerializedName



data class Main(
    @SerializedName("temp") val temp: Double?,
    @SerializedName("temp_min") val tempMin: Double?,
    @SerializedName("temp_max") val tempMax: Double?,
    @SerializedName("pressure") val pressure: Double?,
    @SerializedName("sea_level") val seaLevel: Double?,
    @SerializedName("grnd_level") val grndLevel: Double?,
    @SerializedName("humidity") val humidity: Int?,
    @SerializedName("temp_kf") val tempKf: Double?
               )