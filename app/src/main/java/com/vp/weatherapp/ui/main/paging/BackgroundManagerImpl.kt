package com.vp.weatherapp.ui.main.paging

import android.view.View

import com.vp.weatherapp.ui.main.paging.helpers.ColorHelper


class BackgroundManagerImpl(private val colors: List<Int>) : BackgroundManager {

    init {
        if (colors.isEmpty()) {
            throw IllegalArgumentException("colors must have at least one element")
        }
    }

    override fun updateBackground(background: View, index: Int, offset: Float) {

        if (index > colors.size - 1) {
            throw IllegalArgumentException("index is too large")
        }

        val colorLeft = colors[index]

        val isLast = index == colors.size - 1
        val colorRight = if (isLast) colors[index] else colors[index + 1]

        background.setBackgroundColor(ColorHelper.blendColors(colorLeft, colorRight, offset))
    }
}