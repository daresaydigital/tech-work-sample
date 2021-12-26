package com.daresaydigital.domain.features.top_rated_movie.repository

import com.daresaydigital.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface TopRatedMoviesRepository {
    fun getTopRatedMovies(page:Int): Flow<List<MovieDomain>>
}