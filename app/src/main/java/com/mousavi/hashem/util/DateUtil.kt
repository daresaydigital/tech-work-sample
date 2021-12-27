package com.mousavi.hashem.util

import java.text.SimpleDateFormat
import java.util.*

private val calendar = Calendar.getInstance()
fun dateFormat(dateString: String?): String {
    if (dateString == null) return ""
    if (!dateString.contains("-")) return ""

    val arr = dateString.split("-")
    val year = arr[0].toIntOrNull() ?: return ""
    val month = arr[1].toIntOrNull() ?: return ""
    val day = arr[2].toIntOrNull() ?: return ""
    calendar.set(Calendar.YEAR, year)
    calendar.set(Calendar.MONTH, month - 1)//in java month is zero index but api get Jan as 1
    calendar.set(Calendar.DAY_OF_MONTH, day)
    val date = Date(calendar.timeInMillis)
    val dateFormatter = SimpleDateFormat("yyyy MMM d", Locale.US)
    return dateFormatter.format(date) ?: ""
}