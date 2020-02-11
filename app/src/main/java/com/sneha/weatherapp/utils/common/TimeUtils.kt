package com.sneha.weatherapp.utils.common

import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*

object TimeUtils {

    private const val SECOND_MILLIS = 1000
    private const val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private const val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private const val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(time: Long): String {

        val time = Date(time * 1000L).time

        val now = System.currentTimeMillis()
        if (time > now || time <= 0) return ""

        val diff = now - time
        return if (diff < MINUTE_MILLIS) {
            "just now"
        } else if (diff < 2 * MINUTE_MILLIS) {
            "a minute ago"
        } else if (diff < 50 * MINUTE_MILLIS) {
            "${diff / MINUTE_MILLIS} minutes ago"
        } else if (diff < 90 * MINUTE_MILLIS) {
            "an hour ago"
        } else if (diff < 24 * HOUR_MILLIS) {
            "${diff / HOUR_MILLIS} hours ago"
        } else if (diff < 48 * HOUR_MILLIS) {
            "yesterday"
        } else {
            "${diff / DAY_MILLIS} days ago"
        }
    }

    fun getTimeInExpectedFormat(dt: Long, format: String): String {
        val date = Date(dt * 1000L)
        val sdf: SimpleDateFormat = when (format) {
            "HH:mm" -> SimpleDateFormat("hh:mm", Locale.US)
            "EEE" -> SimpleDateFormat("EEE", Locale.US)
            "EEEE" -> SimpleDateFormat("EEEE", Locale.US)
            else -> SimpleDateFormat("yyyy-MM-dd", Locale.US)
        }
        sdf.timeZone = TimeZone.getTimeZone("UTC")
        return sdf.format(date)
    }

    fun changeDateToHourFormat(date: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        val expectedDate = sdf.parse(date)

        val formatter = SimpleDateFormat("HH:mm", Locale.US)
        val format = formatter.format(expectedDate)
        return format
    }
}