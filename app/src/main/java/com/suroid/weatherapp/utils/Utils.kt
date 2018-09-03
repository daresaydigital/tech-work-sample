package com.suroid.weatherapp.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.gson.reflect.TypeToken
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