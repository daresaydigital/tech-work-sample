package com.movies.tmdb.data.remote

import com.movies.tmdb.data.remote.responses.MovieDetailsResponse
import com.movies.tmdb.data.remote.responses.MoviesResponse
import com.movies.tmdb.data.remote.responses.ReviewResponse
import com.movies.tmdb.other.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {



    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY

    ): Response<MoviesResponse>



    @GET("movie/popular")
    suspend fun getMostPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY

    ): Response<MoviesResponse>


    @GET("movie/popular")
    suspend fun getMovieDetails(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY

    ): Response<MovieDetailsResponse>




    @GET("movie/{movie_id}/reviews")
    suspend fun getMovieReview(
        @Path("movie_id") movieId:Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String = API_KEY

    ): Response<ReviewResponse>
}