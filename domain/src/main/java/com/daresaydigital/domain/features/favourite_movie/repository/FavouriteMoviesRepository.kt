package com.daresaydigital.domain.features.favourite_movie.repository

import com.daresaydigital.domain.model.Movie
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface FavouriteMoviesRepository {
    suspend fun insertFavouriteMovie(favMovie: Movie)
    suspend fun getFavouriteMovieById(id: Int): Movie?
    suspend fun removeFavouriteMovie(id: Int)
    suspend fun getAllFavouriteMovies(): List<Movie>
    fun getAllFavouriteMoviesStream(): Flow<Result<List<Movie>>>
}