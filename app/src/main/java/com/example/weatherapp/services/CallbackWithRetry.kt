package com.example.weatherapp.services

import retrofit2.Call
import retrofit2.Callback

abstract class CallbackWithRetry<T> : Callback<T> {

    private val TOTAL_RETRIES = 3
    private var retryCount = 0

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        if (retryCount > TOTAL_RETRIES) {
            onFailureAfterRetry(call, t)
            return
        }
        retry(call)
        retryCount++
    }

    //Clone the call and retry
    private fun retry(call: Call<T>?) {
        call?.clone()?.enqueue(this)
    }

    abstract fun onFailureAfterRetry(call: Call<T>?, t: Throwable?)
}