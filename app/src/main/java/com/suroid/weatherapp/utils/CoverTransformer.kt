package com.suroid.weatherapp.utils

import android.support.v4.view.ViewPager
import android.view.View

private const val SCALE_MIN = 0.3f
private const val SCALE_MAX = 1f

class CoverTransformer(private val scale: Float) : ViewPager.PageTransformer {

    override fun transformPage(page: View, position: Float) {

        if (scale != 0f) {
            val realScale = getFloat(1 - Math.abs(position * scale), SCALE_MIN, SCALE_MAX)
            page.scaleX = realScale
            page.scaleY = realScale
        }
    }

    private fun getFloat(value: Float, minValue: Float, maxValue: Float): Float {
        return Math.min(maxValue, Math.max(minValue, value))
    }
}
