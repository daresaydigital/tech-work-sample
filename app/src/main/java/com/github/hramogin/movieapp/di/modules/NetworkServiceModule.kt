package com.github.hramogin.movieapp.di.modules

import com.github.hramogin.movieapp.data.repository.movie.MovieService
import org.koin.dsl.module
import retrofit2.Retrofit

val networkServiceModule = module {
    single { get<Retrofit>().create(MovieService::class.java) }
}