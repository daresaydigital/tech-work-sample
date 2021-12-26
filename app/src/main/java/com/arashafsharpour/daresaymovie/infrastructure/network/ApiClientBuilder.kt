package com.arashafsharpour.daresaymovie.infrastructure.network

import android.util.Log
import timber.log.Timber

class ApiClientBuilder<T>(private val apiCall: suspend () -> T) {

    private var _onError: ((java.lang.Exception) -> Unit)? = null
    private var _beforeCall: (() -> Unit)? = null
    private var _afterCall: (() -> Unit)? = null
    private var _onSuccess: ((T) -> Unit)? = null

    fun onSuccess(callback: (T) -> Unit): ApiClientBuilder<T> = apply {
        _onSuccess = callback
    }

    fun onError(callback: (java.lang.Exception) -> Unit): ApiClientBuilder<T> = apply {
        _onError = callback
    }

    fun beforeCall(callback: () -> Unit): ApiClientBuilder<T> = apply {
        _beforeCall = callback
    }

    fun afterCall(callback: () -> Unit): ApiClientBuilder<T> = apply {
        _afterCall = callback
    }

    suspend fun call() {
        _beforeCall?.invoke()
        try {
            val response = apiCall.invoke()
            _onSuccess?.invoke(response)
        } catch (e: Exception) {
            Timber.e(e)
            _onError?.invoke(e)
        }
        _afterCall?.invoke()
    }

}