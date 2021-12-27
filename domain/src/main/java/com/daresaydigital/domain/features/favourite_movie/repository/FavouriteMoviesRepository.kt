package com.daresaydigital.domain.features.favourite_movie.repository

import com.daresaydigital.domain.features.favourite_movie.model.FavMovie
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface FavouriteMoviesRepository {
    suspend fun insertFavouriteMovie(favMovie: FavMovie)
    suspend fun getFavouriteMovieById(id: Int): FavMovie?
    suspend fun removeFavouriteMovie(id: Int)
    suspend fun getAllFavouriteMovies(): List<FavMovie>
    fun getAllFavouriteMoviesStream(): Flow<Result<List<FavMovie>>>
}