package com.movies.tmdb.repositories

import androidx.lifecycle.LiveData
import com.movies.tmdb.data.remote.responses.MovieDetailsResponse
import com.movies.tmdb.data.remote.responses.MovieObject
import com.movies.tmdb.data.remote.responses.MoviesResponse
import com.movies.tmdb.data.remote.responses.ReviewResponse
import com.movies.tmdb.other.Resource

interface MoviesRepository {

    suspend fun insertShoppingItem(shoppingItem: MovieObject)
    suspend fun deleteShoppingItem(id: Int)
    fun observeAllShoppingItems(): LiveData<List<MovieObject>>
    suspend fun isMovieExistsOffline(id: Int): Boolean

    suspend fun getListOfMovies(filterId: Int, page: Int): Resource<MoviesResponse>
    suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailsResponse>
    suspend fun getMovieReviews(movieId: Int, page: Int): Resource<ReviewResponse>

}