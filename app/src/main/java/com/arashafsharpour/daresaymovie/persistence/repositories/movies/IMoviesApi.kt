package com.arashafsharpour.daresaymovie.persistence.repositories.movies

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface IMoviesApi {
    @GET("/3/movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): Movies

    @GET("/3/movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int
    ): Movies

    @GET("/3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): Movies

    @GET("/3/movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("page") page: Int
    ): Movies
}