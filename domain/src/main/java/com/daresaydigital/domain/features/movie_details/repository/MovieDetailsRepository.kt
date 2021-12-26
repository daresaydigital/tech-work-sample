package com.daresaydigital.domain.features.movie_details.repository

import com.daresaydigital.domain.features.movie_details.model.MovieDetailsDomain
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id:Int): Flow<Result<MovieDetailsDomain>>
}