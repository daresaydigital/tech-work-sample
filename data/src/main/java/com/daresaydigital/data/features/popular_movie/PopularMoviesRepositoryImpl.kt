package com.daresaydigital.data.features.popular_movie

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.features.popular_movie.local.PopularMoviesLocalDataSource
import com.daresaydigital.data.features.popular_movie.model.toPopularMoviesDomain
import com.daresaydigital.data.features.popular_movie.remote.PopularMoviesRemoteDataSource
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.domain.features.popular_movie.model.PopularMoviesDomain
import com.daresaydigital.domain.features.popular_movie.repository.PopularMoviesRepository
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: PopularMoviesRemoteDataSource,
    private val localDataSource: PopularMoviesLocalDataSource,
    private val globalDispatcher: GlobalDispatcher,
    private val scope: CoroutineScope
) : PopularMoviesRepository{

    companion object {
        private const val UNKNOWN_API_EXCEPTION = "unknown api exception"
    }

    override fun getPopularMovies(page: Int): Flow<Result<PopularMoviesDomain>> {
        return flow {
            val localJob = scope.launch(globalDispatcher.io){
                localDataSource.getAllPopularMovies().collect {
                    it?.let {
                        emit(Result.Success(it.toPopularMoviesDomain()))
                    }
                }
            }

            val remoteResult = remoteDataSource.getPopularMovies(page)
            localJob.cancel()
            when(remoteResult){
                is ApiResult.Success -> {
                    emit(Result.Success(remoteResult.value.toPopularMoviesDomain()))
                    //todo store to db
                }
                is ApiResult.Failure -> {
                    emit(Result.Failure(remoteResult.error.message ?: UNKNOWN_API_EXCEPTION))
                }
            }
        }
    }

}