package com.ukhanoff.rainbeforeseven.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherModel(

        @SerializedName("weather") var weather: List<Weather>,

        @SerializedName("main") var main: Main,

        @SerializedName("wind") var wind: Wind,

        @SerializedName("sys") var sys: Sys,

        @SerializedName("name") var name: String

)

data class Weather(

        @SerializedName("id") val id: Int? = null,

        @SerializedName("main") val main: String? = null,

        @SerializedName("description") val description: String? = null,

        @SerializedName("icon") val icon: String? = null

)

data class Main(

        @SerializedName("temp") val temp: Double? = null,

        @SerializedName("temp_min") val tempMin: Double? = null,

        @SerializedName("temp_max") val tempMax: Double? = null,

        @SerializedName("humidity") val humidity: Double? = null,

        @SerializedName("pressure") val pressure: Double? = null

)

data class Wind(

        @SerializedName("speed") val speed: Float? = null,

        @SerializedName("deg") val deg: Float? = null
)


data class Sys(

        @SerializedName("message") val message: Double? = null,

        @SerializedName("sunrise") val sunrise: Int? = null,

        @SerializedName("sunset") val sunset: Int? = null
)