package com.arashafsharpour.daresaymovie.persistence.database.sharedpreference

import android.content.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SharedPreferencesHelper(private val sharedPreferences: SharedPreferences) :
    ISharedPreferencesHelper {

    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    override fun setValue(key: String, value: Any?, scope: CoroutineScope?) {
        if (scope != null) {
            scope.launch(Dispatchers.IO) {
                putValue(key, value)
            }
        } else {
            putValue(key, value)
        }
    }

    override fun getString(key: String, defValue: String): String {
        return sharedPreferences.getString(key, defValue) ?: ""
    }

    override fun getInt(key: String, defValue: Int): Int {
        return sharedPreferences.getInt(key, defValue)
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defValue)
    }

    override fun getFloat(key: String, defValue: Float): Float {
        return sharedPreferences.getFloat(key, defValue)
    }

    override fun getLong(key: String, defValue: Long): Long {
        return sharedPreferences.getLong(key, defValue)
    }

    override fun clearSharedPreferences() {
        editor.clear().commit()
    }

    private fun putValue(key: String, value: Any?) {
        when (value) {
            is String -> editor.putString(key, value)
            is Int -> editor.putInt(key, value)
            is Boolean -> editor.putBoolean(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            else -> throw UnsupportedOperationException("Not yet implemented")
        }
        editor.commit()
    }
}