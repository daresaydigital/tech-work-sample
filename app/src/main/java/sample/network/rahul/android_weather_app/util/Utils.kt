package sample.network.rahul.android_weather_app.util

import java.text.SimpleDateFormat
import java.util.*

object Utils {

    fun millisecondToTime(timeStamp: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = timeStamp*1000L
        val gmt = SimpleDateFormat("hh:mm a")
        return (gmt.format(date.time))
    }
}