package com.sneha.weatherapp.di.component

import com.sneha.weatherapp.di.FragmentScope
import com.sneha.weatherapp.di.module.FragmentModule
import com.sneha.weatherapp.ui.dummies.DummiesFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: DummiesFragment)
}