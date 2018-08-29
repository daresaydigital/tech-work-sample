package com.example.data.reopsitory

import android.app.Application
import android.location.Address
import android.location.Location
import com.example.domain.WeatherRepository
import com.example.domain.model.WeatherResponse
import io.reactivex.Observable
import com.example.data.api.Api
import com.example.domain.model.DailyForecastResponse
import com.example.domain.model.ForecastResponse

class WeatherRepositoryImpl(api: Api) : WeatherRepository {



    private val addressService: AddressService = AddressService()
    private val remoteDataStore: RemoteWeatherDataStore = RemoteWeatherDataStore(api)

    override fun getWeather(city: String): Observable<WeatherResponse> {
        return remoteDataStore.getWeather(city)
    }

    override fun getWeatherByCoord(lat: String, lon: String): Observable<WeatherResponse> {
        return remoteDataStore.getWeatherByCoord(lat,lon)
    }

    override fun getForecastByCoord(lat: String, lon: String): Observable<ForecastResponse> {
        return remoteDataStore.getForecastByCoord(lat,lon)
    }

    override fun getDailyForecastByCoord(lat: String, lon: String, days : Int): Observable<DailyForecastResponse> {
        return remoteDataStore.getDailyForecastByCoord(lat,lon, days)
    }

    override fun getAddress(location: Location, application: Application): Observable<List<Address>> {
        return addressService.getAddress(location,application)
    }


}