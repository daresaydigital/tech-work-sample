package com.github.hramogin.movieapp.di.modules

import com.github.hramogin.movieapp.presentation.screens.details.DetailsViewModel
import com.github.hramogin.movieapp.presentation.screens.moviesList.MoviesListViewModel
import com.github.hramogin.movieapp.presentation.screens.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel() }
    viewModel { MoviesListViewModel(get()) }
    viewModel { (id: String) -> DetailsViewModel(id, get(), get()) }
}