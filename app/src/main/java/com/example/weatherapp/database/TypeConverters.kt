package com.example.weatherapp.database

import androidx.room.TypeConverter
import com.example.weatherapp.models.Weather
import com.google.gson.Gson

object WeatherConverter {
    val gson = Gson()

    @TypeConverter
    @JvmStatic
    fun stringToWeather(data: String?): Weather? {
        if (data.isNullOrEmpty())
            return null

        return gson.fromJson(data, Weather::class.java)
    }

    @TypeConverter
    @JvmStatic
    fun weatherToString(weather: Weather): String? {
        return gson.toJson(weather)
    }
}