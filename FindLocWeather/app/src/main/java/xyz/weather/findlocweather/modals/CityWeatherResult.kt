package xyz.weather.findlocweather.modals

import com.google.gson.annotations.SerializedName

class CityWeatherResult {

    @SerializedName("id")
    val id: Int? = null

    @SerializedName("name")
    val cityName: String? = null

    @SerializedName("main")
    val mainWeatherInfo: MainWeatherInfo? = null

    @SerializedName("sys")
    val sys: Sys? = null

    @SerializedName("weather")
    var weather: List<Weather>? = null

    @SerializedName("dt_txt")
    val date: String? = null

    inner class MainWeatherInfo {

        @SerializedName("temp_max")
        val maxTemp: Double = 0.toDouble()

        @SerializedName("temp_min")
        val minTemp: Double = 0.toDouble()

        @SerializedName("temp")
        val temp: Double = 0.toDouble()

        @SerializedName("pressure")
        val pressure: Double = 0.toDouble()

    }

    inner class Sys {
        @SerializedName("country")
        val country: String? = null

    }

    inner class Weather {

        @SerializedName("id")
        var id: Int? = null

        @SerializedName("main")
        var main: String? = null

        @SerializedName("description")
        var description: String? = null


    }
}

