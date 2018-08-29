package com.example.domain.usecases

import com.example.domain.WeatherRepository
import com.example.domain.model.ForecastResponse
import com.example.domain.model.WeatherResponse
import io.reactivex.Observable

class GetHourlyForecastByCoord(private val weatherRepository : WeatherRepository, lat : String, lon : String) : UseCase<ForecastResponse>() {

    private val mLat : String = lat
    private val mLon : String = lon

    override fun buildUseCaseObservable(): Observable<ForecastResponse> {
        return weatherRepository.getForecastByCoord(mLat,mLon)
    }


}