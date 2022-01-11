package ir.hamidbazargan.daresayassignment.presentation.di

import ir.hamidbazargan.daresayassignment.presentation.detail.DetailViewmodel
import ir.hamidbazargan.daresayassignment.presentation.movielist.MovieListViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val coroutineDispatcherModule = module {
    single { Dispatchers.IO }
}

val viewModelModule = module {
    viewModel(named("detail")) { DetailViewmodel(get(), get(named("GetMovie")), get()) }
    viewModel(named("popular")) { MovieListViewModel(get(), get(named("GetPopularMovies"))) }
    viewModel(named("topRated")) { MovieListViewModel(get(), get(named("GetTopRatedMovies"))) }
    viewModel(named("bookmark")) { MovieListViewModel(get(), get(named("GetBookmarkMovies"))) }
}