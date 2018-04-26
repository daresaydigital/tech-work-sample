package com.vp.weatherapp.util


interface BasePresenter<T> {

    fun stop()

    var view: T
}