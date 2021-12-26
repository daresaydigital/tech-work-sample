package com.daresaydigital.domain.features.movie_details.repository

import com.daresaydigital.domain.model.MovieDomain

interface MovieDetailsRepository {
    suspend fun getMovieDetails(id:Int): Result<MovieDomain>
}