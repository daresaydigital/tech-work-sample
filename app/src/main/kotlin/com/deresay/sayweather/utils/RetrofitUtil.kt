package com.deresay.sayweather.utils

import com.daresay.sayweather.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitUtils {
    private var retrofit: Retrofit? = null


    fun <T> initRetrofit(apiInterface: Class<T>,baseUrl:String = BuildConfig.BASE_URL): T {
        if (retrofit == null || baseUrl != retrofit?.baseUrl().toString()) {

            retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(client)
                    .build()
        }
        return retrofit!!.create(apiInterface)
    }

    /**
     * Ok http client with logging interceptor.
     * @return
     */
    private val client: OkHttpClient
        get() {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60,TimeUnit.SECONDS)
                    .writeTimeout(60,TimeUnit.SECONDS)
                    .build()
            return client
        }
}
