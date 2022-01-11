package ir.hamidbazargan.daresayassignment.domain.di

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.usecase.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCasesModule = module {
    single<UseCaseWithFlow<Int>>(named("GetMovie")) { GetMovie(get()) }
    single<UseCaseWithFlow<Int>>(named("GetPopularMovies")) { GetPopularMovies(get()) }
    single<UseCaseWithFlow<Int>>(named("GetTopRatedMovies")) { GetTopRatedMovies(get()) }
    single<UseCaseWithFlow<Int>>(named("GetBookmarkMovies")) { GetBookmarkMovies(get()) }
    single<UseCase<MovieEntity>> { SaveMovie(get()) }
}