package com.daresaydigital.domain.features.popular_movie.repository

import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import kotlinx.coroutines.flow.Flow
import com.daresaydigital.domain.model.Result

interface PopularMoviesRepository {
    fun getPopularMovies(page:Int): Flow<Result<PopularMovies>>
}