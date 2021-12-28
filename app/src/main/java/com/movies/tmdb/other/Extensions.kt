package com.movies.tmdb.other

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Base64
import android.util.Log
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.util.*


object Extensions {

    fun Bitmap.toBase64String(): String {
        ByteArrayOutputStream().apply {
            compress(Bitmap.CompressFormat.JPEG, 10, this)
            return Base64.encodeToString(toByteArray(), Base64.DEFAULT)
        }
    }

    fun String.base64ToBitmap(): Bitmap? {
        val imageAsBytes = Base64.decode(this.toByteArray(), Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.size)
    }

    fun Context.setAppLocale(language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        return createConfigurationContext(config)
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun Context.isOnline(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
        return false
    }
}