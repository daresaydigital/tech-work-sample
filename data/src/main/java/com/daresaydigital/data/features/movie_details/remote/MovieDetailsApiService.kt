package com.daresaydigital.data.features.movie_details.remote

import com.daresaydigital.data.NetworkConstants
import com.daresaydigital.data.features.movie_details.model.MovieDetailsDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieDetailsApiService {

    @GET(NetworkConstants.MOVIE_DETAIL_ENDPOINT)
    fun getMovieDetails(@Path("id") id:Int): Call<MovieDetailsDTO>

}