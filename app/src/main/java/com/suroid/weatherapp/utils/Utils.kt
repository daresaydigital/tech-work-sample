package com.suroid.weatherapp.utils

import com.suroid.weatherapp.WeatherApplication
import java.io.IOException
import java.nio.charset.Charset

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