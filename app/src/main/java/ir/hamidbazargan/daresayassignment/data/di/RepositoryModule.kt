package ir.hamidbazargan.daresayassignment.data.di

import ir.hamidbazargan.daresayassignment.data.db.MovieDataBase
import ir.hamidbazargan.daresayassignment.data.repository.RepositoryImpl
import ir.hamidbazargan.daresayassignment.domain.repository.Repository
import org.koin.dsl.module

val repositoryModule = module {
    single<Repository> {
        RepositoryImpl(get(), get<MovieDataBase>().MovieDao())
    }
}