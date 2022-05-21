package ir.sass.data.repository

import ir.sass.base_data.model.safeApi
import ir.sass.basedomain.model.Domain
import ir.sass.data.datasource.remote.DiscoverMovieApi
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val discoverMovieApi: DiscoverMovieApi
): MovieRepository {
    override fun discoverMovies(): Flow<Domain<DiscoverMovieModel>> = flow {
        emit(Domain.Progress())
        val response = safeApi { discoverMovieApi.discoverMovies() }

        if(response.isSuccess && response.getOrNull() != null && response.getOrThrow().results != null){
            emit(Domain.Data(response))
        }else{
            val errorMessage = response.getOrNull()?.status_message?:"Error"
            emit(Domain.Data(Result.failure(Throwable(errorMessage))))
        }

    }.flowOn(IO)
}