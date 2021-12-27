package com.daresaydigital.domain.features.movie_details.repository

import com.daresaydigital.domain.features.movie_details.model.MovieDetails
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id:Int): Flow<Result<MovieDetails>>
}