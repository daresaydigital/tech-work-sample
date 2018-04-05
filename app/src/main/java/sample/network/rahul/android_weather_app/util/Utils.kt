package sample.network.rahul.android_weather_app.util

import sample.network.rahul.android_weather_app.R
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    const val API_KEY=""

    fun millisecondToTime(timeStamp: Long): String {
        val date = Calendar.getInstance()
        date.timeInMillis = timeStamp*1000L
        val gmt = SimpleDateFormat("hh:mm a")
        return (gmt.format(date.time))
    }

    fun getWeatherIcon(icon: String?):Int {
        return when(icon){
            "01d" -> R.drawable.ic_sunny_2
            "01n" -> R.drawable.ic_moon
            "02d" -> R.drawable.ic_few_clouds_day
            "02n" -> R.drawable.ic_few_clouds_night
            "03d","03n" -> R.drawable.ic_scattered_clouds
            "04d","04n" -> R.drawable.ic_broken_clouds
            "09d","09n" -> R.drawable.ic_shower_rain
            "010d" -> R.drawable.ic_rain_day
            "010n" -> R.drawable.ic_rain_night
            "11d","11n" -> R.drawable.ic_thunderstorm
            "13d","13n" -> R.drawable.ic_snow
            "50d","50n" -> R.drawable.ic_mist
            else -> R.drawable.ic_sunny_2
        }
    }

}