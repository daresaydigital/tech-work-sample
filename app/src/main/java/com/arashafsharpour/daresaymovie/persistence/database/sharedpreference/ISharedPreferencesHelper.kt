package com.arashafsharpour.daresaymovie.persistence.database.sharedpreference

import kotlinx.coroutines.CoroutineScope

interface ISharedPreferencesHelper {
    fun setValue(key: String, value: Any?, scope: CoroutineScope? = null)
    fun getString(key: String, defValue: String = ""): String
    fun getInt(key: String, defValue: Int = -1): Int
    fun getBoolean(key: String, defValue: Boolean = false): Boolean
    fun getFloat(key: String, defValue: Float = -1f): Float
    fun getLong(key: String, defValue: Long = -1L): Long
    fun clearSharedPreferences()
}