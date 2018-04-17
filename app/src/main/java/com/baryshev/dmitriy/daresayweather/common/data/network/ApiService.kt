package com.baryshev.dmitriy.daresayweather.common.data.network

import com.baryshev.dmitriy.daresayweather.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiService {

    companion object {
        private const val BASE_URL = "http://worksample-api.herokuapp.com/"
        private const val TIMEOUT: Long = 25
        private const val KEY_PARAM = "key"
    }

    fun init(): Api = initRetrofit().create(Api::class.java)

    private fun initRetrofit(): Retrofit {
        val okOkHttpBuilder = OkHttpClient.Builder()
        val okHttpClient = okOkHttpBuilder.readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor { chain -> addApiKeyQueryParam(chain) }
            .build()

        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun addApiKeyQueryParam(chain: Interceptor.Chain): Response? {
        var request = chain.request()
        val url = request.url()
            .newBuilder()
            .addQueryParameter(KEY_PARAM, BuildConfig.API_KEY_VALUE)
            .build()
        request = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(request)
    }

}