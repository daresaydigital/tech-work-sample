package com.example.domain.usecases

import com.example.domain.WeatherRepository
import com.example.domain.model.WeatherResponse
import io.reactivex.Observable

class GetWeatherDataByCoord(private val weatherRepository : WeatherRepository, lat : String, lon : String) : UseCase<WeatherResponse>() {

    private val mLat : String = lat
    private val mLon : String = lon

    override fun buildUseCaseObservable(): Observable<WeatherResponse> {
        return weatherRepository.getWeatherByCoord(mLat,mLon)
    }


}