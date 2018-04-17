package com.vp.weatherapp.ui.initial

import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.data.local.DatabaseHelper
import com.vp.weatherapp.ui.AbstractPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class InitialPresenter(private val weatherRepository: WeatherRepository,
                       private val databaseHelper: DatabaseHelper,
                       override var view: InitialContract.View)
    : AbstractPresenter<InitialContract.View, InitialContract.Presenter>(),
        InitialContract.Presenter, ProgressListener {

    override fun onProgress(percent: Int) = view.onProgress(percent)

    override fun onComplete() = view.onComplete()

    override fun initializeDatabase() {
        databaseHelper.listener = this
        // force Room to initialize the DB by performing a hit on some table
        weatherRepository.getSelectedCities()
                .subscribeOn(Schedulers.io())
                .subscribe()
    }

    override fun stop() {
        super.stop()
        databaseHelper.listener = null
    }
}