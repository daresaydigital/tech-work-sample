package com.github.hramogin.movieapp.di.modules

import com.github.hramogin.movieapp.domain.useCases.movie.GetFilmDetailsUseCase
import com.github.hramogin.movieapp.domain.useCases.movie.GetFilmReviewsUseCase
import com.github.hramogin.movieapp.domain.useCases.movie.GetMoviesUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { GetMoviesUseCase(get()) }
    single { GetFilmDetailsUseCase(get()) }
    single { GetFilmReviewsUseCase(get()) }
}