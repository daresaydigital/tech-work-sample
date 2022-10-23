package com.github.hramogin.movieapp.di.modules

import com.github.hramogin.movieapp.BuildConfig
import com.github.hramogin.movieapp.data.network.TokenInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val REST_API_URL_TAG = "REST_API_URL_TAG"
const val GENERAL_OK_HTTP_TAG = "GENERAL_OK_HTTP_TAG"
const val OPEN_OK_HTTP_TAG = "OPEN_OK_HTTP_TAG"
private const val TIME_OUT = 30L
private const val CACHE_TAG = "CACHE_TAG"

const val GENERAL_BACKEND = "GENERAL_BACKEND"

val networkModule = module {

    single(named(REST_API_URL_TAG)) { BuildConfig.REST_BASE_URL }

    single(named(CACHE_TAG)) { androidApplication().cacheDir }
    single<Interceptor>() { TokenInterceptor() }

    single { getGson() }

    single { Cache(get(named(CACHE_TAG)), 1 * 1024 * 1024) }

    single(named(OPEN_OK_HTTP_TAG)) {
        OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level =
                    if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            })
            .addInterceptor(get<Interceptor>())
            .build()
    } bind OkHttpClient::class

    single { GsonConverterFactory.create(get()) } bind Converter.Factory::class

    single {
        Retrofit.Builder()
            .baseUrl(get(named(REST_API_URL_TAG)) as String)
            .client(get(named(OPEN_OK_HTTP_TAG)))
            .addConverterFactory(get())
            .build()
    }
}

fun getGson(): Gson = GsonBuilder()
    .create()