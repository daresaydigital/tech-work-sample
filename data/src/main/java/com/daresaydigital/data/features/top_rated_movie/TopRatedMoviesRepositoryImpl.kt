package com.daresaydigital.data.features.top_rated_movie

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.features.top_rated_movie.local.TopRatedMoviesLocalDataSource
import com.daresaydigital.data.features.top_rated_movie.model.toDomainArrayModel
import com.daresaydigital.data.features.top_rated_movie.remote.TopRatedMoviesRemoteDataSource
import com.daresaydigital.data.model.toDomainArrayModel
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.domain.features.top_rated_movie.repository.TopRatedMoviesRepository
import com.daresaydigital.domain.model.MovieDomain
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class TopRatedMoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: TopRatedMoviesRemoteDataSource,
    private val localDataSource: TopRatedMoviesLocalDataSource,
    private val globalDispatcher: GlobalDispatcher,
    private val scope: CoroutineScope
) : TopRatedMoviesRepository{

    companion object {
        private const val UNKNOWN_API_EXCEPTION = "unknown api exception"
    }

    override fun getTopRatedMovies(page: Int): Flow<Result<List<MovieDomain>>> {
        return flow {
            val localJob = scope.launch(globalDispatcher.io){
                localDataSource.getAllTopRatedMovies().collect {
                    it?.let {
                        emit(Result.Success(it.toDomainArrayModel()))
                    }
                }
            }

            val remoteResult = remoteDataSource.getTopRatedMovies(page)
            localJob.cancel()
            when(remoteResult){
                is ApiResult.Success -> {
                    emit(Result.Success(remoteResult.value.toDomainArrayModel()))
                }
                is ApiResult.Failure -> {
                    emit(Result.Failure(remoteResult.error.message ?: UNKNOWN_API_EXCEPTION))
                }
            }
        }
    }

}