package com.deresay.sayweather.utils

import android.content.Context
import android.net.ConnectivityManager
import com.deresay.sayweather.models.Wind
import java.text.SimpleDateFormat
import java.util.*


/**
 * Singleton for all general utilities.
 */
object SayWeatherUtil {
    enum class TIMING { MORNING, DAY, EVENING, NIGHT }

    /**
     * Takes a date as argument and returns [TIMING]
     * that denotes a specific period of time.
     */
    fun timing(time: Date): TIMING {
        val hour = SimpleDateFormat("HH", Locale.US)
                .format(time.time)
                .toInt()

        return when {
            (hour > 18) or (hour < 3) -> TIMING.NIGHT
            hour > 15 -> TIMING.EVENING
            hour > 9 -> TIMING.DAY
            else -> {
                TIMING.MORNING
            }
        }

    }


    /**
     * Whether connecting or connected to network.
     */
    fun isConnectedToNetwork(context: Context?): Boolean {
        context?.getSystemService(Context.CONNECTIVITY_SERVICE).apply {
            return when (this) {
                is ConnectivityManager -> {
                    (this.activeNetworkInfo != null && this@apply.activeNetworkInfo.isConnected)
                }
                else -> false
            }
        }
        return false
    }


    /**
     * Temperature Information for display.
     */
    fun temperature(temperature: Int) = "$temperature \u2103"

    /**
     * Make wind information appropriate for display.
     */
    fun wind(wind: Wind) = "Wind : ${wind.speed} mph ${windDirection(wind.degree.toInt())}"

    /**
     * The humidity information for display purpose.
     */
    fun humidity(humidity: Int) = "Humidity : $humidity %"

    /**
     * Calculates wind direction from degree value.
     */
    fun windDirection(degree: Int) = when {
        (degree > 0) && (degree < 90) -> "NE"
        (degree > 90) && (degree < 180) -> "SE"
        (degree > 180) && (degree < 270) -> "SW"
        (degree > 270) && (degree < 360) -> "NW"
        (degree == 0) || (degree == 360) -> "N"
        degree == 90 -> "E"
        degree == 180 -> "S"
        degree == 270 -> "W"
        else -> ""
    }

}
