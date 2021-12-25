package com.mousavi.hashem.util

val monthNames = arrayOf("Jan", "Feb", "Mar", "Apr", "May", "Jun",
    "Jul", "Aug", "Sep", "Oct", "Nov", "Dec")

fun dateFormat(dateString: String?): String {
    if (dateString == null) return ""
    if (!dateString.contains("-")) return ""

    val arr = dateString.split("-")
    if (arr.size != 3) return ""
    val month = arr[1].toIntOrNull() ?: return ""

    return arr[0] + " ${monthNames[month - 1]} " + arr[2]
}