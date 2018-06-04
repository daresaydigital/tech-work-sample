package com.example.malek.weatherapp.models

import com.google.gson.annotations.SerializedName


data class Main(
        val temp: Double,
        val pressure: Double?,
        val humidity: Int?,
        @SerializedName("temp_min") val tempMin: Double?,
        @SerializedName("temp_max") val tempMax: Double?,
        val sea_level: Double,
        val grnd_level: Double
)