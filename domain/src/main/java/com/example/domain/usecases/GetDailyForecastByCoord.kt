package com.example.domain.usecases

import com.example.domain.WeatherRepository
import com.example.domain.model.DailyForecastResponse
import com.example.domain.model.ForecastResponse
import io.reactivex.Observable

class GetDailyForecastByCoord(private val weatherRepository : WeatherRepository, lat : String, lon : String, days : Int) : UseCase<DailyForecastResponse>() {

    private val mLat : String = lat
    private val mLon : String = lon
    private val mDays : Int = days

    override fun buildUseCaseObservable(): Observable<DailyForecastResponse> {
        return weatherRepository.getDailyForecastByCoord(mLat,mLon, mDays)
    }


}