package com.midnight.weatherforecast.controller

import com.midnight.weatherforecast.intersepts.GlobalIntersepts
import com.midnight.weatherforecast.interfaces.INFApi
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByGeo
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByName
import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater
import com.midnight.weatherforecast.models.modelsResponse.ModelForecast
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 *
 */
class ControllerAPI {
    val API_KEY="62fc4256-8f8c-11e5-8994-feff819cdc9f"
    val API_ADDRESS="http://worksample-api.herokuapp.com"

    private var okHttpClient: OkHttpClient? = null
    private var retrofitRegister: Retrofit? = null
    private var serviceRegister: INFApi? = null

    private var callWeatherByName: Call<ModelCurrentWeater>? = null
    private var callWeatherByGeo: Call<ModelCurrentWeater>? = null

    private var callWeatherByNameSync: Call<ModelCurrentWeater>? = null
    private var callWeatherByGeoSync: Call<ModelCurrentWeater>? = null

    private var callForecastByName: Call<ModelForecast>? = null


    private object Holder { val INSTANCE = ControllerAPI()}
    companion object {
        val  instance: ControllerAPI by lazy { ControllerAPI.Holder.INSTANCE}
    }

    /**
     *
     */
    init {
        initOkHttp()
        initBuilder()
        initService()
    }

    /**
     *
     */
    private fun initOkHttp() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient = OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(GlobalIntersepts())
                .addInterceptor(interceptor)
                .build()
    }

    /**
     *
     */
    private fun initBuilder() {
        retrofitRegister = Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient!!)
                .baseUrl(API_ADDRESS)
                .build()
    }

    /**
     *
     */
    private fun initService() {
        serviceRegister = retrofitRegister!!.create(INFApi::class.java)
    }

    /**
     *
     */
    fun getCurrentWeatherByName(paramCurrentWeatherByName: ModelParamCurrentWeatherByName, callback: Callback<ModelCurrentWeater>) {
        callWeatherByName = serviceRegister!!.currentWeaterByCityName(paramCurrentWeatherByName.cityName,paramCurrentWeatherByName.key)
        callWeatherByName!!.enqueue(callback)
    }

    /**
     *
     */
    fun getCurrentWeatherByGPS(paramCurrentWeatherByGeo: ModelParamCurrentWeatherByGeo, callback: Callback<ModelCurrentWeater>) {
        callWeatherByGeo = serviceRegister!!.currentWeaterByCityGeo(paramCurrentWeatherByGeo.lat,paramCurrentWeatherByGeo.lon,paramCurrentWeatherByGeo.key)
        callWeatherByGeo!!.enqueue(callback)
    }

    /**
     *
     */
    fun getCurrentWeatherByName(paramCurrentWeatherByName: ModelParamCurrentWeatherByName): ModelCurrentWeater? {
        callWeatherByGeoSync = serviceRegister!!.currentWeaterByCityName(paramCurrentWeatherByName.cityName,paramCurrentWeatherByName.key)
        return callWeatherByGeoSync?.execute()?.body()
    }

    /**
     *
     */
    fun getCurrentWeatherByGPS(paramCurrentWeatherByGeo: ModelParamCurrentWeatherByGeo): ModelCurrentWeater? {
        callWeatherByNameSync = serviceRegister!!.currentWeaterByCityGeo(paramCurrentWeatherByGeo.lat,paramCurrentWeatherByGeo.lon,paramCurrentWeatherByGeo.key)
        return callWeatherByGeoSync?.execute()?.body()
    }

    /**
     *
     */
    fun getForecastWeatherByName(paramCurrentWeatherByName: ModelParamCurrentWeatherByName, callback: Callback<ModelForecast>) {
        callForecastByName = serviceRegister!!.forecastWeaterByCityName(paramCurrentWeatherByName.cityName,paramCurrentWeatherByName.key)
        callForecastByName!!.enqueue(callback)
    }


}