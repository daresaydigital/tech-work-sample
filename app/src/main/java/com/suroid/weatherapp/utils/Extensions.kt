package com.suroid.weatherapp.utils

import com.google.gson.Gson
import java.lang.reflect.Type

fun <T> String.objectify(type: Type): T? {
    if (this.isEmpty()) {
        return null
    }
    try {
        return Gson().fromJson<T>(this, type)
    } catch (ignored: Exception) {
    }

    return null
}