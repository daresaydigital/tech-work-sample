package com.vp.weatherapp.ui.main.paging

import android.view.View


interface BackgroundManager {

    fun updateBackground(background: View, index: Int, offset: Float)
}