package com.arashafsharpour.daresaymovie.infrastructure.extensions

import androidx.test.espresso.idling.CountingIdlingResource


object GlobalIdlingResource {

    private const val resource = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(resource, true)

    fun increment() = countingIdlingResource.increment()

    fun decrement() = countingIdlingResource.decrement()
}