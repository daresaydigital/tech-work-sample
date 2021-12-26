package com.daresaydigital.data.features.top_rated_movie.remote

import com.daresaydigital.data.NetworkConstants
import com.daresaydigital.data.features.top_rated_movie.model.TopRatedMoviesDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TopRatedMoviesApiService {

    @GET(NetworkConstants.TOP_RATED_ENDPOINT)
    fun getTopRatedMovies(@Query("page") id:Int): Call<TopRatedMoviesDTO>

}