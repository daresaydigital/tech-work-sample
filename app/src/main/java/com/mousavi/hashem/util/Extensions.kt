package com.mousavi.hashem.util

import android.content.res.Resources
import kotlin.math.roundToInt


val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

val Int.dpF: Float get() = (this * Resources.getSystem().displayMetrics.density)