package com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen

import android.app.Application
import android.location.Address
import android.location.Location
import com.example.domain.model.*
import com.google.android.gms.location.FusedLocationProviderClient

class MainContract {

    interface View{

        fun shouldShowPermission();

        fun setweatherIcon(iconCode : String)

        fun setWind(speed: String, degreeOld : Float, degreeNew : Float)

        fun setCloudsData(clouds : Clouds)

        fun setMainData(main : Main)

        fun setAdress(address: Address)

        fun setWeather(weather : Weather)

        fun setForecastAdapter(forecastList : List<ListForecast>)

        fun setDewPoint(dewPoint : String)

    }

    interface ViewModel {

        fun init(view: View, fusedLocationClient: FusedLocationProviderClient)

        fun onResume()

        fun onPause()

        fun startLocationUpdates()

    }

    interface Interactor {

        fun cleanUp()

        fun getLocation(location : Location, application: Application)

        fun getWeatherDataByCity(city: String, countryCode: String)

        fun getWeatherDataByCoord(lat: String,lon: String)

        fun getForecastByCoord(lat: String,lon: String)

        fun getDailyForecastByCoord(lat: String,lon: String)

    }

    interface InteractorOutput {

        fun onWeatherDataRecieved(weatherResponse : WeatherResponse)

        fun onForecastDataRecieved(forecastResponse : ForecastResponse)

        fun onDailyForecastDataRecieved(forecastResponse : DailyForecastResponse)

        fun onAdressRecieved(addressList : List<Address>)

    }
}