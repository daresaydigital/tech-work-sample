package ir.hamidbazargan.daresayassignment.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ir.hamidbazargan.daresayassignment.data.webservice.ApiConstants
import ir.hamidbazargan.daresayassignment.data.webservice.WebServiceApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {

    single<Gson> {
        GsonBuilder()
            .setLenient()
            .create()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder().build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
            .create(WebServiceApi::class.java)
    }
}