package com.sneha.weatherapp.di.component

import com.sneha.weatherapp.di.ViewModelScope
import com.sneha.weatherapp.di.module.ViewHolderModule
import com.sneha.weatherapp.ui.weather.adapter.DailyForecastItemViewHolder
import com.sneha.weatherapp.ui.weather.adapter.ForecastItemViewHolder
import dagger.Component

@ViewModelScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ViewHolderModule::class]
)
interface ViewHolderComponent {

    fun inject(viewHolder: ForecastItemViewHolder)

    fun inject(viewHolder: DailyForecastItemViewHolder)
}