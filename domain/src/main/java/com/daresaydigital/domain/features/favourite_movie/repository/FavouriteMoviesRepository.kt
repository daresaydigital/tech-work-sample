package com.daresaydigital.domain.features.favourite_movie.repository

import com.daresaydigital.domain.features.favourite_movie.model.FavMovieDomain
import kotlinx.coroutines.flow.Flow

interface FavouriteMoviesRepository {
    suspend fun insertFavouriteMovie(favMovie: FavMovieDomain)
    suspend fun getFavouriteMovieById(id: Int): FavMovieDomain?
    suspend fun removeFavouriteMovie(id: Int)
    suspend fun getAllFavouriteMovies(): List<FavMovieDomain>
    fun getAllFavouriteMoviesStream(): Flow<List<FavMovieDomain>>
}