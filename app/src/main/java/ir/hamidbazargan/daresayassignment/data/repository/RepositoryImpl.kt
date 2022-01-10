package ir.hamidbazargan.daresayassignment.data.repository

import ir.hamidbazargan.daresayassignment.data.db.MovieDao
import ir.hamidbazargan.daresayassignment.data.mapper.toMovieDataBaseEntity
import ir.hamidbazargan.daresayassignment.data.mapper.toMovieEntity
import ir.hamidbazargan.daresayassignment.data.webservice.WebServiceApi
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity
import ir.hamidbazargan.daresayassignment.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RepositoryImpl(
    private val webServiceApi: WebServiceApi,
    private val movieDao: MovieDao
) : Repository {
    override suspend fun getPopularMovies(page: Int): Flow<List<MovieEntity>> = flow {
        webServiceApi.getPopularMovies(page).let { response ->
            emit(
                mutableListOf<MovieEntity>().apply {
                    response.results.forEach { movie ->
                        add(movie.toMovieEntity())
                    }
                }
            )
        }
    }

    override suspend fun getTopRatedMovies(page: Int): Flow<List<MovieEntity>> = flow {
        webServiceApi.getTopRatedMovies(page).let { response ->
            emit(
                mutableListOf<MovieEntity>().apply {
                    response.results.forEach { movie ->
                        add(movie.toMovieEntity())
                    }
                }
            )
        }
    }

    override suspend fun getBookmarkMovies(page: Int): Flow<List<MovieEntity>> = flow {
        emit(
            movieDao.queryMovies().map { movie ->
                movie.toMovieEntity()
            }
        )
    }


    override suspend fun getMovie(id: Int): Flow<MovieEntity> = flow {
        movieDao.queryMovie(id)?.let { movie ->
            emit(movie.toMovieEntity())
        } ?: run {
            emit(
                webServiceApi.getMovie(id).toMovieEntity()
            )
        }
    }

    override suspend fun saveMovie(movie: MovieEntity) =
        movieDao.insertMovie(movie.toMovieDataBaseEntity())
}