package ir.hamidbazargan.daresayassignment.domain.repository

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getPopularMovies(page: Int): Flow<List<MovieEntity>>
    suspend fun getTopRatedMovies(page: Int): Flow<List<MovieEntity>>
    suspend fun getBookmarkMovies(page: Int): Flow<List<MovieEntity>>
    suspend fun getMovie(id: Int): Flow<MovieEntity>
    suspend fun saveMovie(movie: MovieEntity)
}