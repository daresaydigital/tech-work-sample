package com.arashafsharpour.daresaymovie.persistence.repositories.profile

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Account
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movies
import retrofit2.http.GET
import retrofit2.http.Query

interface IProfileApi {
    @GET("/3/account")
    suspend fun getProfile(
        @Query("api_key")apiKey: String
    ): Account

    @GET("/3/movie/500/recommendations")
    suspend fun getFavoriteMovies(
        @Query("page") page: Int
    ): Movies

    @GET("/3/movie/550/recommendations")
    suspend fun getWatchLaterMovies(
        @Query("page") page: Int
    ): Movies
}