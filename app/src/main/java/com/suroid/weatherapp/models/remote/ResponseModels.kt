package com.suroid.weatherapp.models.remote

/**
 * All the models needed to parse api response are in this class
 */
data class ListResponseModel(val list: ArrayList<WeatherResponseModel>)

data class WeatherResponseModel(val main: Main, val weather: ArrayList<Weather>, val wind: Wind,
                                val dt: Long, val id: Int) {

    fun getWeather(): Weather? {
        return if (weather.isNotEmpty()) weather[0] else null
    }

}

data class Wind(val speed: Float)

data class Main(val temp: Float,
                val humidity: Int,
                val temp_min: Float,
                val temp_max: Float)

data class Weather(val id: Int,
                   val main: String,
                   val description: String,
                   val icon: String)
