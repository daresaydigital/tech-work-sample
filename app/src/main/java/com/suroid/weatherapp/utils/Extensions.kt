package com.suroid.weatherapp.utils

import android.graphics.drawable.TransitionDrawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.widget.ImageView
import com.google.gson.Gson
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.models.TemperatureModel
import com.suroid.weatherapp.models.WeatherModel
import com.suroid.weatherapp.models.remote.WeatherResponseModel
import java.lang.reflect.Type

val gson = Gson()

fun <T> String.objectify(type: Type): T? {
    if (this.isEmpty()) {
        return null
    }
    try {
        return gson.fromJson<T>(this, type)
    } catch (ignored: Exception) {
    }

    return null
}

fun Any.jsonify(): String? {
    try {
        return gson.toJson(this)
    } catch (ignored: Exception) {
    }

    return null
}

fun ImageView.fadeInImage(@DrawableRes drawableRes: Int) {
    ContextCompat.getDrawable(context, drawableRes)?.let {
        val td = TransitionDrawable(arrayOf(this.drawable, it))
        this.setImageDrawable(td)
        td.isCrossFadeEnabled = true
        td.startTransition(1000)
    }
}

/**
 * Extension function to push the loading status to the observing responseStatus
 * */
fun WeatherResponseModel.mapToWeatherEntity(cityWeather: CityWeatherEntity): CityWeatherEntity {
    val temperatureModel = TemperatureModel(main.temp, main.temp_min, main.temp_max)
    val weatherModel = WeatherModel(getWeather()?.main, getWeather()?.description, temperatureModel, wind.speed, main.humidity, getWeather()?.id)
    return CityWeatherEntity(id, weatherModel, city = cityWeather.city, date = currentTimeInSeconds())
}