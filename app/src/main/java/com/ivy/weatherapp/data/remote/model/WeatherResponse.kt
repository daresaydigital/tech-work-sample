package com.ivy.weatherapp.data.remote.model

import android.arch.persistence.room.Entity
import com.google.gson.annotations.SerializedName

@Entity
data class WeatherResponse(
        val name: String = "",
        val main: MainInfo = MainInfo(),
        val weather: List<WeatherInfo> = emptyList(),
        @SerializedName("dt") val timestamp: Long = 0
)

data class MainInfo(
        val temp: Double = 0.0,
        val humidity: Int = 0,
        @SerializedName("temp_min") val tempMin: Double = 0.0,
        @SerializedName("temp_max") val tempMax: Double = 0.0
)

data class WeatherInfo(
        val description: String = "",
        val icon: String = ""
)