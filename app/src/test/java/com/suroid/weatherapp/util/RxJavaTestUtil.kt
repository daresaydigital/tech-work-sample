package com.suroid.weatherapp.util

import io.reactivex.Single
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


object RxJavaTestUtil {
    fun <T> getValue(single: Single<T>): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val d = single.subscribe ({
            data = it
            latch.countDown()
        }, {
            latch.countDown()
        })
        latch.await(2, TimeUnit.SECONDS)
        d.dispose()

        @Suppress("UNCHECKED_CAST")
        return data!!
    }
}
