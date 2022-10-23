package com.github.hramogin.movieapp.data.repository.movie

import com.github.hramogin.movieapp.data.model.ListResponse
import com.github.hramogin.movieapp.data.model.MovieApi
import com.github.hramogin.movieapp.data.model.MovieDetailsApi
import com.github.hramogin.movieapp.data.model.ReviewApi
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieService {

    @GET("movie/top_rated")
    suspend fun getFilms(): ListResponse<MovieApi>

    @GET("movie/{movie_id}")
    suspend fun getFilmDetails(@Path("movie_id") movieId: String): MovieDetailsApi

    @GET("movie/{movie_id}/reviews")
    suspend fun getFilmReviews(@Path("movie_id") movieId: String): ListResponse<ReviewApi>
}