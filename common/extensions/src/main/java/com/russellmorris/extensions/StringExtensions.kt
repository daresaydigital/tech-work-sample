package com.russellmorris.extensions

import java.text.SimpleDateFormat
import java.util.*

fun String.parseUtcDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val formatter = SimpleDateFormat("E, MMM dd yyyy", Locale.getDefault())
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    return formatter.format(parser.parse(this))
}