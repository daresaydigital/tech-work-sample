package com.daresaydigital.data.features.top_rated_movie

import com.daresaydigital.data.features.top_rated_movie.model.toTopRatedMoviesDomain
import com.daresaydigital.data.features.top_rated_movie.remote.TopRatedMoviesRemoteDataSource
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.domain.features.top_rated_movie.model.TopRatedMovies
import com.daresaydigital.domain.features.top_rated_movie.repository.TopRatedMoviesRepository
import com.daresaydigital.domain.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: TopRatedMoviesRemoteDataSource,
) : TopRatedMoviesRepository{

    companion object {
        private const val UNKNOWN_API_EXCEPTION = "unknown api exception"
    }

    override suspend fun getTopRatedMovies(page: Int): Result<TopRatedMovies> {
        return when (val apiResult = remoteDataSource.getTopRatedMovies(page)) {
            is ApiResult.Success -> {
                val domainObject = apiResult.value.toTopRatedMoviesDomain()
                Result.Success(domainObject)
            }
            is ApiResult.Failure -> {
                Result.Failure(apiResult.error.message ?: UNKNOWN_API_EXCEPTION)
            }
        }
    }

}