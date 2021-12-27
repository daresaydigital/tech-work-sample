package com.daresaydigital.data.features.popular_movie.remote

import com.daresaydigital.core.utils.NetworkConstants
import com.daresaydigital.data.features.popular_movie.model.PopularMoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PopularMovieApiService {

    @GET(NetworkConstants.POPULAR_ENDPOINT)
    fun getPopularMovies(@Query("page") id:Int): Call<PopularMoviesDTO>

}