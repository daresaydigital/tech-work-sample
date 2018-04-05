package sample.network.rahul.android_weather_app.util

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.location.Location
import com.google.gson.Gson
import sample.network.rahul.android_weather_app.R
import sample.network.rahul.android_weather_app.datasource.data.WeatherResponse
import java.text.SimpleDateFormat
import java.util.*


object Utils {

    const val API_KEY = ""
    private const val PREFS_NAME = "open_weather"

    fun millisecondToTime(timeStamp: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = timeStamp * 1000L
        val gmt = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return (gmt.format(date.time))
    }

    fun getWeatherIcon(icon: String?): Int {
        return when (icon) {
            "01d" -> R.drawable.ic_sunny_2
            "01n" -> R.drawable.ic_moon
            "02d" -> R.drawable.ic_few_clouds_day
            "02n" -> R.drawable.ic_few_clouds_night
            "03d", "03n" -> R.drawable.ic_scattered_clouds
            "04d", "04n" -> R.drawable.ic_broken_clouds
            "09d", "09n" -> R.drawable.ic_shower_rain
            "010d" -> R.drawable.ic_rain_day
            "010n" -> R.drawable.ic_rain_night
            "11d", "11n" -> R.drawable.ic_thunderstorm
            "13d", "13n" -> R.drawable.ic_snow
            "50d", "50n" -> R.drawable.ic_mist
            else -> R.drawable.ic_sunny_2
        }
    }

    fun fetchWeatherFromLocal(context: Context): WeatherResponse? {
        val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val restoredText = prefs.getString("lastWeather", null)
        if (restoredText != null) {
            return Gson().fromJson(restoredText, WeatherResponse::class.java)
        }
        return null
    }

    fun storeWeatherLocal(context: Context, response: WeatherResponse?) {
        if (response != null) {
            val gson = Gson()
            val json = gson.toJson(response)
            val editor = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
            editor.putString("lastWeather", json)
            editor.apply()
        }
    }

    fun fetchLastLocationFromLocal(context: Context): Location? {
        val prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val lat = prefs.getString("lat", null)
        val lon = prefs.getString("lon", null)
        if (lat != null && lon != null) {
            val location = Location("last")
            location.latitude = lat.toDouble()
            location.longitude = lon.toDouble()
            return location
        }
        return null
    }

    fun storeLastLocationLocal(context: Context, location: Location?) {
        if (location != null) {
            val editor = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
            editor.putString("lat", location.latitude.toString())
            editor.putString("lon", location.longitude.toString())
            editor.apply()
        }
    }


}