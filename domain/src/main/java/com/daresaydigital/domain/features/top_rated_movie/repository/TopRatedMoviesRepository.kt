package com.daresaydigital.domain.features.top_rated_movie.repository

import com.daresaydigital.domain.features.top_rated_movie.model.TopRatedMovies
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface TopRatedMoviesRepository {
    fun getTopRatedMovies(page:Int): Flow<Result<TopRatedMovies>>
}