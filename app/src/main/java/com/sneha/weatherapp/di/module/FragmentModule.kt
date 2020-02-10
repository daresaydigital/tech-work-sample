package com.sneha.weatherapp.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sneha.weatherapp.data.repository.WeatherRepository
import com.sneha.weatherapp.ui.base.BaseFragment
import com.sneha.weatherapp.ui.weather.fragment.WeatherFragmentViewModel
import com.sneha.weatherapp.utils.ViewModelProviderFactory
import com.sneha.weatherapp.utils.network.NetworkHelper
import com.sneha.weatherapp.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    fun provideWeatherFragmentViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        weatherRepository: WeatherRepository
    ): WeatherFragmentViewModel =
        ViewModelProviders.of(fragment,
            ViewModelProviderFactory(WeatherFragmentViewModel::class) {
                WeatherFragmentViewModel(schedulerProvider, compositeDisposable, networkHelper, weatherRepository)
            }
        ).get(WeatherFragmentViewModel::class.java)

//    @Provides
//    fun provideWeatherAdapter() = WeatherAdapter(fragment.lifecycle, ArrayList())
}