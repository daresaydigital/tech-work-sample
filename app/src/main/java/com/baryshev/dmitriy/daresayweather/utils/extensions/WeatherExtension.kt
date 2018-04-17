package com.baryshev.dmitriy.daresayweather.utils.extensions

import com.baryshev.dmitriy.daresayweather.App
import com.baryshev.dmitriy.daresayweather.R
import kotlin.math.roundToInt

fun Double.temp(): String = (if (this > 0) "+" else "") + this.roundToInt().toStringOrEmpty() +" " + App.context().getString(R.string.cels)

fun Double.mmHg(): String = (0.75006157584566 * this).roundToInt().toStringOrEmpty() + " " + App.context().getString(R.string.mmHg)

fun Double.wind(): String = this.roundToInt().toStringOrEmpty() + " " + App.context().getString(R.string.m_per_s)

fun Int.humidity(): String = this.toStringOrEmpty() + " " + App.context().getString(R.string.percent)