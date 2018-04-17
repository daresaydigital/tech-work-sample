package com.vp.weatherapp.data.local.fts

import com.google.gson.annotations.SerializedName


data class CityJson(
        @SerializedName("id")       val id:         Long,
        @SerializedName("name")     val name:       String,
        @SerializedName("country")  val country:    String,
        @SerializedName("coord")    val coord:      CoordJson
)

data class CoordJson(
        @SerializedName("lat") val lat: Double,
        @SerializedName("lon") val lon: Double
)