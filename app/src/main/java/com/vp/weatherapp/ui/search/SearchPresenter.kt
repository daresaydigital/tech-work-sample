package com.vp.weatherapp.ui.search

import android.annotation.SuppressLint
import android.util.Log
import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.data.local.entity.CityEntity
import com.vp.weatherapp.ui.AbstractPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class SearchPresenter(private val weatherRepository: WeatherRepository,
                      override var view: SearchContract.View)
    : AbstractPresenter<SearchContract.View, SearchContract.Presenter>(),
        SearchContract.Presenter {

    @SuppressLint("RxLeakedSubscription")
    override fun performSearch(text: String) {
        Log.e("SEARCH", "performSearch : $text")
        launch {
            weatherRepository.performSearch(text)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { cities -> view.searchComplete(cities) },
                            { err ->  {} }
                    )
        }
    }

    @SuppressLint("RxLeakedSubscription")
    override fun saveSelectedCity(city: CityEntity) {
        launch {
            weatherRepository.saveSelectedCity(city)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { result -> view.onCitySelected(result) },
                            { err ->  {} }
                    )
        }
    }


//    override fun getSelectedCities() {
//        launch {
//            weatherRepository.getSelectedCities()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            { view::buildFragments},
//                            { err -> {} }
//                    )
//        }
//    }

}