package com.vp.weatherapp.common

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain!!.request()
        val url = request.url().newBuilder().addQueryParameter("key", API_KEY).build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}