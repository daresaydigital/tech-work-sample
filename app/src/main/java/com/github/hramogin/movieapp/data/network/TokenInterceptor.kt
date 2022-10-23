package com.github.hramogin.movieapp.data.network

import com.github.hramogin.movieapp.BuildConfig
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class TokenInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var original = chain.request()
        val token = getToken()
        val url = original.url.newBuilder().addQueryParameter(API_KEY, token).build()
        original = original.newBuilder().url(url).build()
        return chain.proceed(original)
    }

    private fun getToken(): String {
        return runBlocking {
            try {
                BuildConfig.API_TOKEN
            } catch (exception: Exception) {
                Timber.e(exception)
                ""
            }
        }
    }

    private companion object {
        const val API_KEY = "api_key"
    }
}