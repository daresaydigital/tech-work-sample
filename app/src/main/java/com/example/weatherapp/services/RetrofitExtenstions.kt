package com.example.weatherapp.services

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.enqueueWithRetry(callback: Callback<T>) {
    this.enqueue(object : CallbackWithRetry<T>() {
        override fun onFailureAfterRetry(call: Call<T>?, t: Throwable?) {
            callback.onFailure(call, t)
        }

        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            callback.onResponse(call, response)
        }

        // The super onFailure method will do the retry
    })
}