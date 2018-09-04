package com.suroid.weatherapp.utils

import com.google.gson.Gson
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