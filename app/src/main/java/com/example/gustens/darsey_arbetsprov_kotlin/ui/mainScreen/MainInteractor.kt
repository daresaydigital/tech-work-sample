package com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen

import android.app.Application
import android.location.Address
import android.location.Location
import android.util.Log
import com.example.domain.WeatherRepository
import com.example.domain.model.DailyForecastResponse
import com.example.domain.model.ForecastResponse
import com.example.domain.model.WeatherResponse
import com.example.domain.usecases.*
import com.example.gustens.darsey_arbetsprov_kotlin.app.App


import io.reactivex.observers.DisposableObserver
import javax.inject.Inject


class MainInteractor(application: Application, private val mOutput: MainContract.InteractorOutput) : MainContract.Interactor {

    private val LOG_TAG = MainInteractor::class.java.name

    @field:[Inject]
    internal lateinit var weatherRepository: WeatherRepository

    private var mGetAddress : GetAddress? = null
    private var mGetWeatherDataByCity: GetWeatherDataByCity? = null
    private var mGetWeatherDataByCoord: GetWeatherDataByCoord? = null
    private var mGetForecastByCoord: GetHourlyForecastByCoord? = null
    private var mGetDailyForecastByCoord: GetDailyForecastByCoord? = null


    init {
        (application.applicationContext as App).mainComponent.inject(this)
    }



    override fun getLocation(location: Location, application: Application) {

        if(mGetAddress != null) {
            mGetAddress!!.dispose()
        }

        mGetAddress =  GetAddress( weatherRepository , location, application)

        mGetAddress!!.execute(AddressObserver())

    }


    override fun getWeatherDataByCity(city: String, countryCode: String) {

        if(mGetWeatherDataByCity != null) {
            mGetWeatherDataByCity!!.dispose()
        }

        mGetWeatherDataByCity =  GetWeatherDataByCity(weatherRepository,city,countryCode)

        mGetWeatherDataByCity!!.execute(WeatherDataObserver())

    }


    override fun getWeatherDataByCoord(lat: String, lon: String) {
        if(mGetWeatherDataByCoord != null) {
            mGetWeatherDataByCoord!!.dispose()
        }

        mGetWeatherDataByCoord =  GetWeatherDataByCoord(weatherRepository,lat,lon)

        mGetWeatherDataByCoord!!.execute(WeatherDataObserver())
    }

    override fun getForecastByCoord(lat: String, lon: String) {
        if(mGetForecastByCoord != null) {
            mGetForecastByCoord!!.dispose()
        }

        mGetForecastByCoord = GetHourlyForecastByCoord(weatherRepository,lat,lon)

        mGetForecastByCoord!!.execute(ForecastDataObserver())
    }


    override fun getDailyForecastByCoord(lat: String, lon: String) {
        if(mGetDailyForecastByCoord != null) {
            mGetDailyForecastByCoord!!.dispose()
        }

        mGetDailyForecastByCoord = GetDailyForecastByCoord(weatherRepository,lat,lon,10)

        mGetDailyForecastByCoord!!.execute(DailyForecastDataObserver())

    }


    // Dispose
    override
    fun cleanUp() {
        if(mGetWeatherDataByCity != null) {
            mGetWeatherDataByCity!!.dispose()
        }

        if(mGetForecastByCoord != null) {
            mGetForecastByCoord!!.dispose()
        }

        if(mGetWeatherDataByCity != null) {
            mGetAddress!!.dispose()
        }

        if(mGetDailyForecastByCoord != null) {
            mGetDailyForecastByCoord!!.dispose()
        }
    }


    private inner class WeatherDataObserver : DisposableObserver<WeatherResponse>() {
        override fun onComplete() {

        }

        override fun onNext(t: WeatherResponse) {
            mOutput.onWeatherDataRecieved(t)
        }

        override fun onError(e: Throwable) {

        }
    }

    private inner class ForecastDataObserver : DisposableObserver<ForecastResponse>() {
        override fun onComplete() {

        }

        override fun onNext(t: ForecastResponse) {
            mOutput.onForecastDataRecieved(t)
        }

        override fun onError(e: Throwable) {
            // Todo
            Log.e(LOG_TAG, "Forecast onError "+e.printStackTrace())
        }
    }


    private inner class DailyForecastDataObserver : DisposableObserver<DailyForecastResponse>() {
        override fun onComplete() {

        }

        override fun onNext(t: DailyForecastResponse) {
           mOutput.onDailyForecastDataRecieved(t)
        }

        override fun onError(e: Throwable) {
            // Todo
            Log.e(LOG_TAG, "Daily Forecast onError "+e.printStackTrace())
        }
    }


    private inner class AddressObserver : DisposableObserver<List<Address>>() {
        override fun onComplete() {

        }

        override fun onNext(t: List<Address>) {
            mOutput.onAdressRecieved(t)
        }

        override fun onError(e: Throwable) {
            // Todo
        }
    }



}