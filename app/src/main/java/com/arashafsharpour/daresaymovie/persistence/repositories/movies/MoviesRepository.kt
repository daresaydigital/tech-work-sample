package com.arashafsharpour.daresaymovie.persistence.repositories.movies

import com.arashafsharpour.daresaymovie.infrastructure.models.MovieCategoryType
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movies
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val moviesApi: IMoviesApi
): IMoviesRepository {

    override fun getMoviesByType(categoryType: MovieCategoryType, page: Int): ApiClientBuilder<Movies> {
        return when(categoryType) {
            MovieCategoryType.POPULAR -> {
                ApiClientBuilder { moviesApi.getPopularMovies(page) }
            }
            MovieCategoryType.NOWPLAYING -> {
                ApiClientBuilder { moviesApi.getNowPlayingMovies(page) }
            }
            MovieCategoryType.UPCOMING -> {
                ApiClientBuilder { moviesApi.getUpcomingMovies(page) }
            }
            else -> { ApiClientBuilder { moviesApi.getTopRatedMovies(page) } }
        }
    }
}