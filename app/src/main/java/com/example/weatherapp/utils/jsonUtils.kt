package com.example.weatherapp.utils

import android.content.Context
import com.example.weatherapp.models.City
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.nio.charset.Charset

class jsonUtils {
    companion object {
        private fun getJsonInAsset(fileName: String, context: Context): String? {
            var json: String? = null
            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charset.defaultCharset())
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return json
        }

        fun getCitiesFromAsset(context: Context): ArrayList<City> {
            val jsonString = getJsonInAsset("cities_500k.json", context)
            val gson = Gson()
            return gson.fromJson(jsonString, object : TypeToken<ArrayList<City>>() {}.type)
        }
    }
}