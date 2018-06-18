package com.ukhanoff.rainbeforeseven.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherModel(@SerializedName("weather") var weather: Weather,
                               @SerializedName("main") var main: Main,
                               @SerializedName("wind") var wind: Wind)
{

    class Weather(@SerializedName("id") val id: Int? = null,
                  @SerializedName("main") val main: String? = null,
                  @SerializedName("description") var description: String? = null,
                  @SerializedName("icon") val icon: String? = null)

    class Main(@SerializedName("temp") val temp: Float? = null,
               @SerializedName("temp_min") val temp_min: Int? = null,
               @SerializedName("temp_max") val temp_max: Int? = null,
               @SerializedName("humidity") val humidity: Int? = null,
               @SerializedName("pressure") val pressure: Int? = null)

    class Wind(@SerializedName("speed") var speed: Float? = null,
               @SerializedName("deg") var deg: Float? = null)

}
