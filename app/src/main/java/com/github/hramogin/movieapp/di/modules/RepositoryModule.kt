package com.github.hramogin.movieapp.di.modules

import com.github.hramogin.movieapp.data.repository.movie.MovieRepository
import com.github.hramogin.movieapp.data.repository.movie.MovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}