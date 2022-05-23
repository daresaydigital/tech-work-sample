package ir.sass.movie.data.datasource.remote

import ir.sass.movie.data.model.movie.DiscoverMovieDto
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverMovieApi {
    @GET("/3/movie/popular")
    suspend fun discoverPopularMovies(@Query("page") page : Int): DiscoverMovieDto

    @GET("/3/movie/top_rated")
    suspend fun discoverTopMovies(@Query("page") page : Int): DiscoverMovieDto
}