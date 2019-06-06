package xyz.weather.findlocweather.constants

import android.graphics.Color

object WeatherConstants {

    //Include emoji constants here
    // to be returned related to the weather data

    private val WINDY = "\uD83C\uDF2C"
    private val CLEAR = "☀️"
    private val CLOUDY = "☁️"
    private val RAINY = "☔️"
    private val SNOWY = "\uD83C\uDF28"
    private val SMOKE = "\uD83C\uDF2B"
    private val DRIZZLE = "\uD83D\uDCA7"

    private val BRIGHT_BLUE = "#62abff"
    private val MOODY_BLUE = "#bbcee3"
    private val GRAY = "#ced5dd"

    fun getWeatherEmoji(weatherInfo: String): String {

        //In my solution (assumption) these info like "clear", "wind" act like constants

        if (weatherInfo == null || "" == weatherInfo)
            return ""

        return if (weatherInfo.contains("clear")) {
            CLEAR
        } else if (weatherInfo.contains("wind")) {
            WINDY
        } else if (weatherInfo.contains("snow")) {
            SNOWY
        } else if (weatherInfo.contains("cloud")) {
            CLOUDY
        } else if (weatherInfo.contains("rain")) {
            RAINY
        } else if (weatherInfo.contains("smoke")) {
            SMOKE
        } else if (weatherInfo.contains("drizzle")) {
            DRIZZLE
        } else
            ""

    }

    fun getWeatherBackground(weatherInfo: String): Int {
        if (weatherInfo == null || "" == weatherInfo)
            return Color.parseColor("#ffffff")

        return if (weatherInfo.contains("clear")) {
            Color.parseColor(BRIGHT_BLUE)
        } else if (weatherInfo.contains("wind")) {
            Color.parseColor(MOODY_BLUE)
        } else if (weatherInfo.contains("snow")) {
            Color.parseColor(GRAY)
        } else if (weatherInfo.contains("cloud")) {
            Color.parseColor(MOODY_BLUE)
        } else if (weatherInfo.contains("rain")) {
            Color.parseColor(MOODY_BLUE)
        } else if (weatherInfo.contains("smoke")) {
            Color.parseColor(GRAY)
        } else if (weatherInfo.contains("drizzle")) {
            Color.parseColor(GRAY)
        } else
            Color.parseColor(MOODY_BLUE)
    }

}