package com.arashafsharpour.daresaymovie.features.movies

import androidx.lifecycle.MutableLiveData
import com.arashafsharpour.daresaymovie.features.movies.infrastracture.IMoviesCoordinator
import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.ExceptionHandler
import com.arashafsharpour.daresaymovie.infrastructure.extensions.launch
import com.arashafsharpour.daresaymovie.infrastructure.extensions.notifyObserver
import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movie
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewModel
import com.arashafsharpour.daresaymovie.persistence.repositories.movies.IMoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesFragmentViewModel
@Inject constructor(
    val coordinator: IMoviesCoordinator,
    private val movieRepository: IMoviesRepository,
    override val exceptionHandler: ExceptionHandler
) : BaseViewModel(coordinator) {

    val popularMovies = MutableLiveData(mutableListOf<Movie>())
    val nowPlayingMovies = MutableLiveData(mutableListOf<Movie>())
    val upcomingMovies = MutableLiveData(mutableListOf<Movie>())
    val topRatedMovies = MutableLiveData(mutableListOf<Movie>())

    init {
        if (popularMovies.value.isNullOrEmpty()) {
            getPopularMovies(categoryType = MovieCategoryType.POPULAR)
            getUpcomingMovies(categoryType = MovieCategoryType.UPCOMING)
            getNowPlayingMovies(categoryType = MovieCategoryType.NOWPLAYING)
            getTopRatedMovies(categoryType = MovieCategoryType.TOPRATED)
        }
    }

    fun getPopularMovies(categoryType: MovieCategoryType, page: Int = 1) = launch {
        movieRepository.getMoviesByType(categoryType, page)
            .onSuccess { setPopularMoviesList(it.result, categoryType) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    fun getTopRatedMovies(categoryType: MovieCategoryType, page: Int = 1) = launch {
        movieRepository.getMoviesByType(categoryType, page)
            .onSuccess { setPopularMoviesList(it.result, categoryType) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    fun getUpcomingMovies(categoryType: MovieCategoryType, page: Int = 1) = launch {
        movieRepository.getMoviesByType(categoryType, page)
            .onSuccess { setPopularMoviesList(it.result, categoryType) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    fun getNowPlayingMovies(categoryType: MovieCategoryType, page: Int = 1) = launch {
        movieRepository.getMoviesByType(categoryType, page)
            .onSuccess { setPopularMoviesList(it.result, categoryType) }
            .onError { exceptionHandler.handle(it, coordinator) }
            .call()
    }

    private fun setPopularMoviesList(result: ArrayList<Movie>, categoryType: MovieCategoryType) {
        if (!result.isNullOrEmpty())
        when (categoryType) {
            MovieCategoryType.TOPRATED -> {
                topRatedMovies.value?.addAll(result)
                topRatedMovies.notifyObserver()
            }
            MovieCategoryType.UPCOMING -> {
                upcomingMovies.value?.addAll(result)
                upcomingMovies.notifyObserver()
            }
            MovieCategoryType.POPULAR -> {
                popularMovies.value?.addAll(result)
                popularMovies.notifyObserver()
            }
            MovieCategoryType.NOWPLAYING -> {
                nowPlayingMovies.value?.addAll(result)
                nowPlayingMovies.notifyObserver()
            }
            else -> {}
        }
    }
}