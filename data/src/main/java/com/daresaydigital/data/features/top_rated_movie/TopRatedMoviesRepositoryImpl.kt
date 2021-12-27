package com.daresaydigital.data.features.top_rated_movie

import com.daresaydigital.data.features.top_rated_movie.local.TopRatedMoviesLocalDataSource
import com.daresaydigital.data.features.top_rated_movie.model.toTopRatedMoviesDomain
import com.daresaydigital.data.features.top_rated_movie.remote.TopRatedMoviesRemoteDataSource
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.domain.features.top_rated_movie.model.TopRatedMovies
import com.daresaydigital.domain.features.top_rated_movie.repository.TopRatedMoviesRepository
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMoviesRepositoryImpl @Inject constructor(
    private val remoteDataSource: TopRatedMoviesRemoteDataSource,
    private val localDataSource: TopRatedMoviesLocalDataSource,
) : TopRatedMoviesRepository{

    companion object {
        private const val UNKNOWN_API_EXCEPTION = "unknown api exception"
    }

    override fun getTopRatedMovies(page: Int): Flow<Result<TopRatedMovies>> {
        return flow {
            //todo retrieve from db
//            val localJob = scope.launch(globalDispatcher.io){
//                localDataSource.getAllTopRatedMovies().collect {
//                    it?.let {
//                        emit(Result.Success(it.toTopRatedMoviesDomain()))
//                    }
//                }
//            }

            when(val remoteResult = remoteDataSource.getTopRatedMovies(page)){
                is ApiResult.Success -> {
                    emit(Result.Success(remoteResult.value.toTopRatedMoviesDomain()))
                    //todo store to db
                }
                is ApiResult.Failure -> {
                    emit(Result.Failure(remoteResult.error.message ?: UNKNOWN_API_EXCEPTION))
                }
            }
        }
    }

}