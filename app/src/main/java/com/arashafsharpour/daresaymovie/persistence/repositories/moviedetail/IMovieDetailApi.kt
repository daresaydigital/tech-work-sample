package com.arashafsharpour.daresaymovie.persistence.repositories.moviedetail

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Credits
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.MovieDetail
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.MovieImages
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Review
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IMovieDetailApi {
    @GET("/3/movie/{movie_id}")
    suspend fun getPopularMovies(
        @Path("movie_id") movieId: String
    ): MovieDetail

    @GET("/3/movie/{movie_id}/credits")
    suspend fun getCredit(
        @Path("movie_id") movieId: String
    ): Credits

    @GET("/3/movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: String,
        @Query("page") page: Int
    ): Review

    @GET("/3/movie/{movie_id}/images")
    suspend fun getImages(
        @Path("movie_id") movieId: String
    ): MovieImages
}