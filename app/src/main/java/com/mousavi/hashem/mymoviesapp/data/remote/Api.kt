package com.mousavi.hashem.mymoviesapp.data.remote

import com.mousavi.hashem.mymoviesapp.data.remote.dto.GenresDto
import com.mousavi.hashem.mymoviesapp.data.remote.dto.PageDataDto
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "7457b8e2de9fbd83fe4e24eaa79912f2"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): PageDataDto

    @GET("genre/movie/list")
    suspend fun getGenres(): GenresDto
}