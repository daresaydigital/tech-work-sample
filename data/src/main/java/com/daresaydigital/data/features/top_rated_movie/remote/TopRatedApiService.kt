package com.daresaydigital.data.features.top_rated_movie.remote

import com.daresaydigital.data.NetworkConstants
import com.daresaydigital.data.model.MovieDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TopRatedApiService {

    @GET(NetworkConstants.TOP_RATED_ENDPOINT)
    fun getTopRatedMovies(@Query("page") id:Int): Call<List<MovieDTO>>

}