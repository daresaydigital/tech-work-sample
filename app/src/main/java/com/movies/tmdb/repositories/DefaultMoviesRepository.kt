package com.movies.tmdb.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import com.movies.tmdb.data.local.MoviesDao
import com.movies.tmdb.data.remote.TmdbApi
import com.movies.tmdb.data.remote.responses.MovieDetailsResponse
import com.movies.tmdb.data.remote.responses.MovieObject
import com.movies.tmdb.data.remote.responses.MoviesResponse
import com.movies.tmdb.data.remote.responses.ReviewResponse
import com.movies.tmdb.other.Constants
import com.movies.tmdb.other.Resource
import retrofit2.Response
import javax.inject.Inject

class DefaultMoviesRepository @Inject constructor(
    private val moviesDao: MoviesDao,
    private val tmdbApi: TmdbApi
) : MoviesRepository {

    override suspend fun insertShoppingItem(shoppingItem: MovieObject) {
        moviesDao.insertMovie(shoppingItem)
    }

    override suspend fun deleteShoppingItem(id: Int) {
        moviesDao.deleteMovie(id)
    }

    override fun observeAllShoppingItems(): LiveData<List<MovieObject>> {
        return moviesDao.observeAllMovies()
    }

    override suspend fun isMovieExistsOffline(id: Int): Boolean {
        return moviesDao.isMovieExistsOffline(id)
    }

    override suspend fun getListOfMovies(filterId: Int, page: Int): Resource<MoviesResponse> {
        return try {
            var response: Response<MoviesResponse>? = null
            when (filterId) {
                Constants.FilterId.TOP_RATED.filterId -> response =
                    tmdbApi.getTopRatedMovies(page)
                Constants.FilterId.MOST_POPULAR.filterId -> response =
                    tmdbApi.getMostPopularMovies(page)
            }
            if (response!!.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getMovieDetails(movieId: Int): Resource<MovieDetailsResponse> {
        return try {
            var response = tmdbApi.getMovieDetails(movieId)

            if (response.isSuccessful) {
                response.body().let {
                     return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }

    override suspend fun getMovieReviews(movieId: Int, page: Int): Resource<ReviewResponse> {
        return try {
            var response = tmdbApi.getMovieReview(movieId, page)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occured", null)
            } else {
                Resource.error("An unknown error occured", null)
            }
        } catch (e: Exception) {
            Log.e("EXCEPTION", "EXCEPTION:", e)
            Resource.error("Couldn't reach the server. Check your internet connection", null)
        }
    }
}














