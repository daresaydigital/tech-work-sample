package com.baryshev.dmitriy.daresayweather.utils.extensions

/**
 * 4/13/2018.
 */
fun Any?.toStringOrEmpty(): String = toStringOrEmpty { "" }

inline fun Any?.toStringOrEmpty(code: () -> String): String = this?.toString() ?: code()

inline fun String?.orEmpty(code: () -> String): String = if (this.isNullOrEmpty()) code() else this ?: code()