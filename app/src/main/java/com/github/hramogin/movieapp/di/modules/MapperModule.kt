package com.github.hramogin.movieapp.di.modules

import com.github.hramogin.movieapp.presentation.screens.details.MovieDetailsMapper
import org.koin.dsl.module

val mapperModule = module {

    single { MovieDetailsMapper(get())}
}