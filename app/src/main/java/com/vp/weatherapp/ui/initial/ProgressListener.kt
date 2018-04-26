package com.vp.weatherapp.ui.initial


interface ProgressListener {
    fun onProgress(percent: Int)
    fun onComplete()
}