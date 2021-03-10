package com.daresay.movies.data.api

import com.daresay.movies.data.models.authentication.SessionRequestBody
import javax.inject.Inject

class TmdbRemoteDataSource @Inject constructor(private val tmdbService: TmdbService) : BaseDataSource() {
    suspend fun getToken() = getResult {
        tmdbService.getToken()
    }

    suspend fun createSession(requestBody: SessionRequestBody) = getResult {
        tmdbService.createSession(requestBody = requestBody)
    }

    suspend fun getTopRatedMovies(page: Int) = getResult {
        tmdbService.getTopRatedMovies(page = page)
    }

    suspend fun getPopularMovies(page: Int) = getResult {
        tmdbService.getPopularMovies(page = page)
    }

    suspend fun getMovieDetails(movieId: Int) = getResult {
        tmdbService.getMovieDetails(movieId = movieId)
    }

    suspend fun getMovieReviews(movieId: Int, page: Int) = getResult {
        tmdbService.getMovieReviews(movieId = movieId, page = page)
    }
}