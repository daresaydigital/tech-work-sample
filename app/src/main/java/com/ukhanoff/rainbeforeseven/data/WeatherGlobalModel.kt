package com.ukhanoff.rainbeforeseven.data

import com.google.gson.annotations.SerializedName

data class WeatherGlobalModel(

        @SerializedName("weather") var weather: List<Weather>,

        @SerializedName("main") var main: Main,

        @SerializedName("wind") var wind: Wind,

        @SerializedName("sys") var sys: Sys,

        @SerializedName("name") var name: String,

        @SerializedName("dt") var dt: Long

)

data class Weather(

        @SerializedName("id") val id: Int,

        @SerializedName("main") val main: String,

        @SerializedName("description") val description: String,

        @SerializedName("icon") val icon: String

)

data class Main(

        @SerializedName("temp") val temp: Double,

        @SerializedName("temp_min") val tempMin: Double,

        @SerializedName("temp_max") val tempMax: Double,

        @SerializedName("humidity") val humidity: Double,

        @SerializedName("pressure") val pressure: Double

)

data class Wind(

        @SerializedName("speed") val speed: Float,

        @SerializedName("deg") val deg: Float
)


data class Sys(

        @SerializedName("message") val message: Double? = null,

        @SerializedName("sunrise") val sunrise: Int? = null,

        @SerializedName("sunset") val sunset: Int? = null
)
