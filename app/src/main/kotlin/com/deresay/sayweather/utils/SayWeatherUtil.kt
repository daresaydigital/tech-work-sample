package com.deresay.sayweather.utils

import java.text.SimpleDateFormat
import java.util.*


/**
 * Singleton for all general utilities.
 */
object SayWeatherUtil {
    enum class BACKGROUND_COLOR { MORNING, DAY, EVENING, NIGHT }

    /**
     * Takes a date as argument and returns [BACKGROUND_COLOR]
     * that denotes a specific period of time.
     */
    fun background(date: Date): BACKGROUND_COLOR {
        val hour = SimpleDateFormat("HH", Locale.US)
                .format(date.time)
                .toInt()

        return when {
            (hour > 18) or (hour < 3) -> BACKGROUND_COLOR.NIGHT
            hour > 15 -> BACKGROUND_COLOR.EVENING
            hour > 9 -> BACKGROUND_COLOR.DAY
            else -> {
                BACKGROUND_COLOR.MORNING
            }
        }

    }
}