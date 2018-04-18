package com.baryshev.dmitriy.daresayweather.main.di

import com.baryshev.dmitriy.daresayweather.main.presentation.view.MainActivity
import dagger.Subcomponent

@Subcomponent(modules = [MainModule::class])
@MainScope
interface MainComponent {

    fun inject(activity: MainActivity)

}