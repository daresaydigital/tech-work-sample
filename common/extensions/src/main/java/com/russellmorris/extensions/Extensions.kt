package com.russellmorris.extensions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.SimpleDateFormat
import java.util.*

fun String.parseUtcDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val formatter = SimpleDateFormat("E, MMM dd yyyy", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(parser.parse(this))
}

fun Long.convertToTime(timeZone: Int): String {
    val date = Date((this + timeZone) * 1000)
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    return format.format(date)
}

fun Double.convertToSingleDecimal(): String {
    return BigDecimal(this * MS_TO_KPH).setScale(1, RoundingMode.HALF_EVEN).toString()
}

const val MS_TO_KPH = 3.6
