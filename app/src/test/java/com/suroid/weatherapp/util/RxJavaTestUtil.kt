package com.suroid.weatherapp.util

import io.reactivex.Observable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


object RxJavaTestUtil {
    fun <T> getValue(observable: Observable<T>): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val d = observable.subscribe {
            data = it
            latch.countDown()
        }
        latch.await(2, TimeUnit.SECONDS)
        d.dispose()

        @Suppress("UNCHECKED_CAST")
        return data!!
    }
}
