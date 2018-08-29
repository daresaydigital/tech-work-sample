package com.example.gustens.darsey_arbetsprov_kotlin.di

import com.example.gustens.darsey_arbetsprov_kotlin.app.App
import com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen.MainInteractor
import com.example.gustens.darsey_arbetsprov_kotlin.ui.mainScreen.MainViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [
    (AppModule::class),
    (ApiModule::class),
    (DataModule::class)
])
interface MainComponent {

    fun inject(target: App)

    fun inject(target: MainInteractor)

    fun inject(target: MainViewModel)

}