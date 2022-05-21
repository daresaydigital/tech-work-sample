package ir.sass.data.model.datasource.remote

import ir.sass.data.model.movie.DiscoverMovieDto
import retrofit2.http.GET

interface DiscoverMovieApi {
    @GET("/3/discover/movie")
    fun discoverMovies() : DiscoverMovieDto
}