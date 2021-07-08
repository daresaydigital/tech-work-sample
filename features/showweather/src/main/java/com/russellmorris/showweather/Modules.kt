package com.russellmorris.showweather

import com.russellmorris.showweather.data.repository.WeatherRepositoryImpl
import com.russellmorris.showweather.data.source.WeatherDataSource
import com.russellmorris.showweather.domain.repository.WeatherRepository
import com.russellmorris.showweather.domain.usecase.WeatherUseCase
import com.russellmorris.showweather.service.api.WeatherApi
import com.russellmorris.showweather.service.api.WeatherApiService
import com.russellmorris.network.createNetworkClient
import com.russellmorris.showweather.ui.viewmodel.ShowWeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit

fun injectFeature() = loadFeature

private val loadFeature by lazy {
    loadKoinModules(
        viewModelModule,
        useCaseModule,
        repositoryModule,
        dataSourceModule,
        networkModule
    )
}

val viewModelModule: Module = module {
    viewModel { ShowWeatherViewModel(weatherUseCase = get()) }
}

val useCaseModule: Module = module {
    factory { WeatherUseCase(weatherRepository = get()) }
}

val repositoryModule: Module = module {
    single { WeatherRepositoryImpl(weatherDataSource = get()) as WeatherRepository }
}

val dataSourceModule: Module = module {
    single { WeatherApiService(api = weatherApi) as WeatherDataSource }
}

val networkModule: Module = module {
    single { weatherApi }
}

private const val BASE_URL = BuildConfig.WEATHER_ENDPOINT

private val retrofit: Retrofit = createNetworkClient(BASE_URL, BuildConfig.DEBUG)

private val weatherApi: WeatherApi = retrofit.create(WeatherApi::class.java)