package com.vp.weatherapp.ui.selection

import android.annotation.SuppressLint
import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.ui.AbstractPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SelectionPresenter(private val weatherRepository: WeatherRepository,
                         override var view: SelectionContract.View)
    : AbstractPresenter<SelectionContract.View, SelectionContract.Presenter>(),
        SelectionContract.Presenter {

    @SuppressLint("RxLeakedSubscription")
    override fun getSelectedCities() {
        launch {
            weatherRepository.getSelectedCitiesForecast()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { cities -> view.showSelectedCities(cities) },
                            { err -> {}})
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun deleteSelectedCity(city: CityWithForecast) {
        launch {
            weatherRepository.deleteSelectedCity(city.cityEntity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { success -> view.onCityDeleted() },
                            { err -> {}})
        }
    }

}