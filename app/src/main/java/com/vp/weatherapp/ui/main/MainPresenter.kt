package com.vp.weatherapp.ui.main

import android.annotation.SuppressLint
import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.ui.AbstractPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainPresenter(private val weatherRepository: WeatherRepository,
                       override var view: MainContract.View)
    : AbstractPresenter<MainContract.View, MainContract.Presenter>(),
        MainContract.Presenter {

    @SuppressLint("RxLeakedSubscription")
    override fun getSelectedCities() {
        launch {
            weatherRepository.getSelectedCitiesForecast()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { list -> view.buildFragments(list)},
                            { err -> {} }
                    )
        }
    }

}