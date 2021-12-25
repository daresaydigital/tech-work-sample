package com.mousavi.hashem.util

import java.util.*

val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

val calendar = Calendar.getInstance()

fun dateFormat(dateString: String?): String {
    if (dateString == null) return ""
    if (!dateString.contains("-")) return ""

    val arr = dateString.split("-")
    val year = arr[0].toIntOrNull()
    val month = arr[1].toIntOrNull()
    val day = arr[2].toIntOrNull()

    if (year == null || month == null || day == null) return ""

    return arr[0] + " ${monthNames[month]} " + arr[2]
}