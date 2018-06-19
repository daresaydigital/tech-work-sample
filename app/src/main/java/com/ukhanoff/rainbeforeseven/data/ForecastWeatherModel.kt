package com.ukhanoff.rainbeforeseven.data

import com.google.gson.annotations.SerializedName

data class ForecastWeatherModel(
        @SerializedName("list") var list: List<WeatherGlobalModel>
)
