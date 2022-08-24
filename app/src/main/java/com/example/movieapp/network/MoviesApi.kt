package com.example.movieapp.network

import com.example.movieapp.model.Movie
import com.example.movieapp.model.responses.MoviesResponse
import com.example.movieapp.utils.API_KEY
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApi {

    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") key: String = API_KEY, @Query("page") page: String = "1"): Call<MoviesResponse>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("api_key") key: String = API_KEY, @Query("page") page: String = "1"): Call<MoviesResponse>

    @GET("movie/{movieId}")
    fun getMovieDetails(movieId: Int, @Query("api_key") key: String = API_KEY): Call<Movie>
}