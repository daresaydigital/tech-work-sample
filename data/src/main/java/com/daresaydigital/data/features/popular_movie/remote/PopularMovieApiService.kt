package com.daresaydigital.data.features.popular_movie.remote

import com.daresaydigital.data.NetworkConstants
import com.daresaydigital.data.features.popular_movie.model.PopularMoviesDTO
import com.daresaydigital.data.model.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieApiService {

    @GET(NetworkConstants.POPULAR_ENDPOINT)
    fun getPopularMovies(@Query("page") id:Int): Call<PopularMoviesDTO>

}