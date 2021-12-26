package com.daresaydigital.domain.features.popular_movie.repository

import com.daresaydigital.domain.model.MovieDomain
import kotlinx.coroutines.flow.Flow

interface PopularMoviesRepository {
    fun getPopularMovies(page:Int): Flow<List<MovieDomain>>
}