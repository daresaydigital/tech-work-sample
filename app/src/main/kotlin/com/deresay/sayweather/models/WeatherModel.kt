package com.deresay.sayweather.models

import com.google.gson.annotations.SerializedName

/**
 * Model class for API response [ApiInterface].
 */
data class WeatherModel(@SerializedName("weather") val weather: ArrayList<Weather>,
                        @SerializedName("main") val weatherParams: WeatherParams,
                        @SerializedName("wind") val wind:Wind,
                        @SerializedName("name") val locationName: String)

data class Weather(@SerializedName("id") val id: Long,
                   @SerializedName("main") val main: String,
                   @SerializedName("description") val description: String,
                   @SerializedName("icon") val iconName: String)

data class WeatherParams(@SerializedName("temp") val temperature: Int,
                         @SerializedName("pressure") val pressure: Int,
                         @SerializedName("humidity") val humidity: Int)

data class Wind(@SerializedName("deg") val degree: Float,
                @SerializedName("speed") val speed: Float)