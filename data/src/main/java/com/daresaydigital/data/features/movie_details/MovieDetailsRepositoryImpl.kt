package com.daresaydigital.data.features.movie_details

import com.daresaydigital.data.features.movie_details.local.MovieDetailsLocalDataSource
import com.daresaydigital.data.features.movie_details.remote.MovieDetailsRemoteDataSource
import com.daresaydigital.data.features.movie_details.util.toDomainModel
import com.daresaydigital.data.features.movie_details.util.toLocalModel
import com.daresaydigital.data.util.ApiResult
import com.daresaydigital.domain.features.movie_details.model.MovieDetailsDomain
import com.daresaydigital.domain.features.movie_details.repository.MovieDetailsRepository
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieDetailsRemoteDataSource,
    private val localDataSource: MovieDetailsLocalDataSource
) : MovieDetailsRepository{

    companion object {
        private const val UNKNOWN_API_EXCEPTION = "unknown api exception"
    }

    override suspend fun getMovieDetails(id: Int): Flow<Result<MovieDetailsDomain>> {

        return flow {
            localDataSource.getMovieDetailsById(id)?.toDomainModel()?.let {
                emit(Result.Success(it))
            }

            when(val remoteResult = remoteDataSource.getMovieDetails(id)){
                is ApiResult.Success -> {
                    emit(Result.Success(remoteResult.value.toDomainModel()))
                    localDataSource.insertMovieDetails(remoteResult.value.toDomainModel().toLocalModel())
                }
                is ApiResult.Failure -> {
                    emit(Result.Failure(remoteResult.error.message ?: UNKNOWN_API_EXCEPTION))
                }
            }
        }
    }

}