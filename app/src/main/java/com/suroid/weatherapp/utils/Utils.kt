package com.suroid.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.gson.reflect.TypeToken
import com.suroid.weatherapp.R
import com.suroid.weatherapp.WeatherApplication
import com.suroid.weatherapp.models.City
import java.io.IOException
import java.nio.charset.Charset

val CITY_ARRAY_LIST_TYPE = object : TypeToken<ArrayList<City>>() {}.type

fun loadJSONFromAsset(fileName: String): String? {
    return try {
        val inputStream = WeatherApplication.coreComponent.context().assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.defaultCharset())
    } catch (ex: IOException) {
        ex.printStackTrace()
        null
    }
}

fun hideKeyboard(activity: Activity, view: View?) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.let {
        imm.hideSoftInputFromWindow(it.windowToken, 0)
    } ?: run {
        activity.currentFocus?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
        }
    }
}

fun showKeyboard(activity: Activity, view: View?) {
    val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    view?.let {
        imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
    } ?: run {
        activity.currentFocus?.let {
            imm.showSoftInput(it, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

fun currentTimeInSeconds(): Long {
    return System.currentTimeMillis()/1000
}

fun weatherIconForId(id: Int): Int {
    return when {
        id > 800 -> R.drawable.ic_clouds
        id == 800 -> R.drawable.ic_clear
        id > 700 -> R.drawable.ic_atmosphere
        id > 600 -> R.drawable.ic_snow
        id > 300 -> R.drawable.ic_rain
        id > 200 -> R.drawable.ic_thunder_storm
        else -> R.drawable.ic_clear
    }
}

fun weatherImageForId(id: Int): Int {
    return when {
        id > 800 -> R.drawable.image_windy
        id == 800 -> R.drawable.image_clear
        id > 700 -> R.drawable.image_windy
        id > 600 -> R.drawable.image_snow
        id > 300 -> R.drawable.image_rain
        id > 200 -> R.drawable.image_rain
        else -> R.drawable.image_clear
    }
}