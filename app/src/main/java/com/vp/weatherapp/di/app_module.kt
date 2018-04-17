package com.vp.weatherapp.di

import com.vp.weatherapp.data.WeatherRepository
import com.vp.weatherapp.data.WeatherRepositoryImpl
import com.vp.weatherapp.di.Params.INITIAL_VIEW
import com.vp.weatherapp.di.Params.MAIN_VIEW
import com.vp.weatherapp.di.Params.SEARCH_VIEW
import com.vp.weatherapp.di.Params.SELECTION_VIEW
import com.vp.weatherapp.di.Params.WEATHER_VIEW
import com.vp.weatherapp.ui.initial.InitialContract
import com.vp.weatherapp.ui.initial.InitialPresenter
import com.vp.weatherapp.ui.main.MainContract
import com.vp.weatherapp.ui.main.MainPresenter
import com.vp.weatherapp.ui.main.WeatherContract
import com.vp.weatherapp.ui.main.WeatherPresenter
import com.vp.weatherapp.ui.search.SearchContract
import com.vp.weatherapp.ui.search.SearchPresenter
import com.vp.weatherapp.ui.selection.SelectionContract
import com.vp.weatherapp.ui.selection.SelectionPresenter
import org.koin.dsl.module.applicationContext


val weatherModule = applicationContext {

    factory { params -> InitialPresenter(get(), get(), params[INITIAL_VIEW]) as InitialContract.Presenter}

    factory { params -> MainPresenter(get(), params[MAIN_VIEW]) as MainContract.Presenter}

    factory { params -> SearchPresenter(get(), params[SEARCH_VIEW]) as SearchContract.Presenter}

    factory { params -> SelectionPresenter(get(), params[SELECTION_VIEW]) as SelectionContract.Presenter}

    factory { params -> WeatherPresenter(get(), params[WEATHER_VIEW]) as WeatherContract.Presenter}

    bean { WeatherRepositoryImpl(get(), get()) as WeatherRepository }
}

object Params {
    const val INITIAL_VIEW = "INITIAL_VIEW"
    const val MAIN_VIEW = "MAIN_VIEW"
    const val SEARCH_VIEW = "SEARCH_VIEW"
    const val SELECTION_VIEW = "SELECTION_VIEW"
    const val WEATHER_VIEW = "WEATHER_VIEW"
}

val weatherApp = listOf(weatherModule)