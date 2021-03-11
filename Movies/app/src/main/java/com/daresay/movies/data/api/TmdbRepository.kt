package com.daresay.movies.data.api

import com.daresay.movies.data.local.MovieDao
import com.daresay.movies.data.models.authentication.SessionRequestBody
import com.daresay.movies.data.models.favorites.Favorite
import com.daresay.movies.utils.performDatabaseGetOperation
import com.daresay.movies.utils.performGetOperation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TmdbRepository @Inject constructor(
    private val remoteDataSource: TmdbRemoteDataSource,
    private val localDataSource: MovieDao) {

    fun getUserToken() = performGetOperation(
        networkCall = { remoteDataSource.getToken() }
    )

    fun createSession(requestBody: SessionRequestBody) = performGetOperation(
        networkCall = { remoteDataSource.createSession(requestBody = requestBody) }
    )

    fun getTopRatedMovies(page: Int) = performGetOperation(
        networkCall = { remoteDataSource.getTopRatedMovies(page = page) }
    )

    fun getPopularMovies(page: Int) = performGetOperation(
        networkCall = { remoteDataSource.getPopularMovies(page = page) }
    )

    fun getMovieDetails(movieId: Int) = performGetOperation(
        databaseQuery = { localDataSource.getMovie(movieId) },
        networkCall = { remoteDataSource.getMovieDetails(movieId) },
        saveCallResult = { localDataSource.insert(it) })

    fun getMovieReviews(movieId: Int, page: Int) = performGetOperation(
        networkCall = { remoteDataSource.getMovieReviews(movieId = movieId, page = page) }
    )

    fun setMovieFavorite(movieId: Int, favorite: Boolean) {
        CoroutineScope(IO).launch {
            localDataSource.insert(Favorite(movieId, favorite))
        }
    }

    fun getFavorite(movieId: Int) = performDatabaseGetOperation(
        databaseQuery = { localDataSource.getFavorite(movieId) }
    )

    fun getAllFavorites() = performDatabaseGetOperation(
        databaseQuery = { localDataSource.getAllFavorites() }
    )
}