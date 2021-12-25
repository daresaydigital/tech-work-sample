package com.mousavi.hashem.util

import android.content.res.Resources
import android.view.View
import kotlin.math.roundToInt


val Int.dp: Int get() = (this * Resources.getSystem().displayMetrics.density).roundToInt()

val Int.dpF: Float get() = (this * Resources.getSystem().displayMetrics.density)

fun View?.show() {
    this?.visibility = View.VISIBLE
}

fun View?.gone() {
    this?.visibility = View.GONE
}

fun View?.hide() {
    this?.visibility = View.INVISIBLE
}

fun View?.showGone(show: Boolean?) {
    if (show == null) return

    if (show) {
        this?.show()
    } else {
        this?.gone()
    }
}