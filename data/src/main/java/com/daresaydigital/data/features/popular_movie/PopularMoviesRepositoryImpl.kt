package com.daresaydigital.data.features.popular_movie

import com.daresaydigital.data.features.popular_movie.local.PopularMoviesLocalDataSource
import com.daresaydigital.data.features.popular_movie.model.toPopularMoviesDomain
import com.daresaydigital.data.features.popular_movie.remote.PopularMoviesRemoteDataSource
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import com.daresaydigital.domain.features.popular_movie.repository.PopularMoviesRepository
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: PopularMoviesRemoteDataSource,
    private val localDataSource: PopularMoviesLocalDataSource,
) : PopularMoviesRepository{

    companion object {
        private const val UNKNOWN_API_EXCEPTION = "unknown api exception"
    }

    override fun getPopularMovies(page: Int): Flow<Result<PopularMovies>> {
        return flow {
            //todo retrieve from db
//            val localJob = scope.launch(globalDispatcher.io){
//                localDataSource.getAllPopularMovies().collect {
//                    it?.let {
//                        emit(Result.Success(it.toPopularMoviesDomain()))
//                    }
//                }
//            }

            when(val remoteResult = remoteDataSource.getPopularMovies(page)){
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