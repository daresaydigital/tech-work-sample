package com.example.domain.usecases

import com.example.domain.WeatherRepository
import com.example.domain.model.WeatherResponse
import io.reactivex.Observable

class GetWeatherDataByCity(private val weatherRepository : WeatherRepository, city : String, countryCode : String) : UseCase<WeatherResponse>() {

    private val mCity : String = city

    override fun buildUseCaseObservable(): Observable<WeatherResponse> {
        return weatherRepository.getWeather(mCity)
    }


}