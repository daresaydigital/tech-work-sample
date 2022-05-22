package ir.sass.movie.data.datasource.remote

import ir.sass.movie.data.model.movie.DiscoverMovieDto
import retrofit2.http.GET

interface DiscoverMovieApi {
    @GET("/3/discover/movie")
    suspend fun discoverMovies(): DiscoverMovieDto
}