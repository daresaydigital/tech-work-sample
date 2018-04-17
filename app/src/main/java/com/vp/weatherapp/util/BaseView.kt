package com.vp.weatherapp.util


interface BaseView<out T : BasePresenter<*>> {

    val presenter: T
}