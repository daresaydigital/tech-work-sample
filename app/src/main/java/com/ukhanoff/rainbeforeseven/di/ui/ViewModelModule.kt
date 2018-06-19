package com.ukhanoff.rainbeforeseven.di.ui

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ukhanoff.rainbeforeseven.di.util.ForApplication
import com.ukhanoff.rainbeforeseven.di.util.ViewModelKey
import com.ukhanoff.rainbeforeseven.viewmodel.WeatherViewModel
import com.ukhanoff.rainbeforeseven.viewmodel.base.AppViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module(includes = [ViewModelModuleBindings::class])
object ViewModelModule

@Module
interface ViewModelModuleBindings {

    @Binds
    @ForApplication
    fun bindsAppViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ForApplication
    @ViewModelKey(WeatherViewModel::class)
    fun bindsWeatherViewModel(mainViewModel: WeatherViewModel): ViewModel

}
