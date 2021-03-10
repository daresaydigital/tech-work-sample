package com.daresay.movies.data.api

import com.daresay.movies.BuildConfig
import com.daresay.movies.data.models.authentication.RequestToken
import com.daresay.movies.data.models.authentication.SessionRequestBody
import com.daresay.movies.data.models.authentication.SessionToken
import com.daresay.movies.data.models.movie.MovieList
import com.daresay.movies.data.models.moviedetails.MovieDetails
import com.daresay.movies.data.models.moviedetails.Reviews
import retrofit2.Response
import retrofit2.http.*

interface TmdbService {
    @GET("authentication/token/new")
    suspend fun getToken(
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<RequestToken>

    @POST("authentication/session/new")
    suspend fun createSession(
        @Body() requestBody: SessionRequestBody,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<SessionToken>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<MovieList>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): Response<MovieList>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("append_to_response") reviews: String = "reviews"
    ): Response<MovieDetails>

    @GET("movie/{movie_id}")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
    ): Response<Reviews>
}