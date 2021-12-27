package com.daresaydigital.data.features.popular_movie

import com.daresaydigital.data.features.popular_movie.model.toPopularMoviesDomain
import com.daresaydigital.data.features.popular_movie.remote.PopularMoviesRemoteDataSource
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import com.daresaydigital.domain.features.popular_movie.repository.PopularMoviesRepository
import com.daresaydigital.domain.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: PopularMoviesRemoteDataSource,
) : PopularMoviesRepository{

    companion object {
        private const val UNKNOWN_API_EXCEPTION = "unknown api exception"
    }

    override suspend fun getPopularMovies(page: Int): Result<PopularMovies> {
        return when (val apiResult = remoteDataSource.getPopularMovies(page)) {
            is ApiResult.Success -> {
                val domainObject = apiResult.value.toPopularMoviesDomain()
                Result.Success(domainObject)
            }
            is ApiResult.Failure -> {
                Result.Failure(apiResult.error.message ?: UNKNOWN_API_EXCEPTION)
            }
        }
    }

}