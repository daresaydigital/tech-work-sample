package com.baryshev.dmitriy.daresayweather.common.data.cache

import android.content.Context
import android.content.SharedPreferences
import com.baryshev.dmitriy.daresayweather.App
import io.reactivex.Single

class CacheRepo {

    companion object {
        const val PREFS_NAME = "com.baryshev.dmitriy.daresayweather.common.data.cache"
        const val KEY_PREF_LAT = "KEY_PREF_LAT"
        const val KEY_PREF_LON = "KEY_PREF_LON"
    }

    private val prefs: SharedPreferences by lazy {
        App.context().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun getLastCoordinates(): Single<Pair<Float, Float>> = Single.fromCallable({ Pair(prefs.getFloat(KEY_PREF_LAT, -1f), prefs.getFloat(KEY_PREF_LON, -1f)) })

    fun isLastCoordsCacheEmpty() = !prefs.contains(KEY_PREF_LAT) || !prefs.contains(KEY_PREF_LON)

    fun saveLastCoordinates(lat:Float, lon:Float){
        prefs.edit().putFloat(KEY_PREF_LAT, lat).apply()
        prefs.edit().putFloat(KEY_PREF_LON, lon).apply()
    }
}