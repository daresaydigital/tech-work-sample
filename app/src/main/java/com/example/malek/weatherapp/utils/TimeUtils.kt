package com.example.malek.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeUtils {
    companion object {
        val hourFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val dayFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        fun getTimeFromTimeStamp(timestamp: Int, simpleDateFormat: SimpleDateFormat): String {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp * 1000L
            return simpleDateFormat.format(calendar.time);
        }
    }


}