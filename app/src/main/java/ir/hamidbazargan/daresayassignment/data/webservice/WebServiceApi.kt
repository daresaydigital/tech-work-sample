package ir.hamidbazargan.daresayassignment.data.webservice

import ir.hamidbazargan.daresayassignment.data.webservice.reponse.MoviesResponse
import ir.hamidbazargan.daresayassignment.data.webservice.reponse.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WebServiceApi {
    @GET(ApiConstants.POPULAR_ENDPOINT)
    suspend fun getPopularMovies(
        @Query("page") page: Int
    ): MoviesResponse

    @GET(ApiConstants.TOP_RATED_ENDPOINT)
    suspend fun getTopRatedMovies(
        @Query("page") page: Int
    ): MoviesResponse

    @GET(ApiConstants.MOVIE_ENDPOINT)
    suspend fun getMovie(
        @Path("id") id: Int
    ): MovieResponse
}