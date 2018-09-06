package com.artamonov.weatherapp

import com.artamonov.weatherapp.data.Weather
import org.json.JSONException
import org.json.JSONObject

object WeatherParser {

    var weather = Weather()

    fun parseJsonWeather(responseJSON: String?, city: String?) {
        val jsonObject: JSONObject?
        try {
            jsonObject = JSONObject(responseJSON)
            val temperature = jsonObject.getJSONObject("main").optDouble("temp")
            val jsonArray = jsonObject.getJSONArray("weather")
            val jsonDescriptions = jsonArray.getJSONObject(0)
            val intro = jsonDescriptions.optString("main")
            val description = jsonDescriptions.optString("description")

            weather = Weather()
            val temperatureInt = temperature.toInt()
            weather.temperature = temperatureInt
            weather.weatherDescription = description
            weather.weatherSummary = intro
            weather.city = city
        } catch (e: JSONException) {
            weather.city = "Unknown"
            weather.weatherSummary = "Unknown"
        }


    }
}
