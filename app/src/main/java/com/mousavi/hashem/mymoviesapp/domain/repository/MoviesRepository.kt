package com.mousavi.hashem.mymoviesapp.domain.repository

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.model.Reviews
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    suspend fun getPopularMovies(
        language: String,
        page: Int,
    ): Either<PageData, String>

    suspend fun getGenres(): Either<Genres, String>

    suspend fun checkIfFavoriteMovie(movie: Movie): Boolean

    fun getFavoriteMoviesFromDatabase(): Flow<List<Movie>>

    suspend fun deleteFavoriteMovieFromDatabase(movie: Movie)

    suspend fun saveToFavoriteMovieDatabase(movie: Movie)

    suspend fun getReviews(
        movieId: Int,
        language: String,
        page: Int,
    ): Either<Reviews, String>
}