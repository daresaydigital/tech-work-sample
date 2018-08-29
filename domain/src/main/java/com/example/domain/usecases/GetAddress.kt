package com.example.domain.usecases

import android.app.Application
import android.location.Address
import android.location.Location
import com.example.domain.WeatherRepository
import io.reactivex.Observable

class GetAddress(private val weatherRepository : WeatherRepository, location : Location, application: Application): UseCase<List<Address>>() {

    private val mLocation : Location = location
    private val mApplication: Application = application

    override fun buildUseCaseObservable(): Observable<List<Address>> {
        return weatherRepository.getAddress(mLocation, mApplication)
    }


}