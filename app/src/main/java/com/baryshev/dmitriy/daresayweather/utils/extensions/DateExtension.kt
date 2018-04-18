package com.baryshev.dmitriy.daresayweather.utils.extensions

import com.baryshev.dmitriy.daresayweather.App
import com.baryshev.dmitriy.daresayweather.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * 4/13/2018.
 */

const val DEFAULT_DATE_PATERN = "dd/MM/yyyy"

fun Long?.formatToDateString(): String =
    this?.let { SimpleDateFormat(DEFAULT_DATE_PATERN, Locale.getDefault()).format(Date(this * 1000L)) } ?: ""

fun Long?.formatToDayOfWeek(): String {
    if (this == null) return ""
    val instance = Calendar.getInstance().apply { timeInMillis = this@formatToDayOfWeek *1000L }
    val dayStringRes = DayWeek.values().firstOrNull { it.dayOrder == instance.get(Calendar.DAY_OF_WEEK) }?.dayStringRes
    return dayStringRes?.let { App.context().getString(dayStringRes) } ?: ""
}

enum class DayWeek(val dayOrder: Int, val dayStringRes: Int) {
    MONDAY(Calendar.MONDAY, R.string.monday),
    TUESDAY(Calendar.TUESDAY, R.string.tuesday),
    WEDNESDAY(Calendar.WEDNESDAY, R.string.wednesday),
    THURSDAY(Calendar.THURSDAY, R.string.thursday),
    FRIDAY(Calendar.FRIDAY, R.string.friday),
    SATURDAY(Calendar.SATURDAY, R.string.saturday),
    SUNDAY(Calendar.SUNDAY, R.string.sunday)

}