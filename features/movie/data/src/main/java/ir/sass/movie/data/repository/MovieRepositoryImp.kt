package ir.sass.movie.data.repository

import ir.sass.base_data.model.safeApi
import ir.sass.base_domain.model.Domain
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.model.ResultModel
import ir.sass.domain.repository.MovieRepository
import ir.sass.movie.data.datasource.remote.DiscoverMovieApi
import ir.sass.movie.data.model.movie.cast
import ir.sass.movie.data.model.movie.castToEntity
import ir.sass.shared_data.db.MovieDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieRepositoryImp @Inject constructor(
    private val discoverMovieApi: DiscoverMovieApi,
    private val dao: MovieDao
) : MovieRepository {
    override fun discoverMovies(): Flow<Domain<DiscoverMovieModel>> = flow {
        emit(Domain.Progress())
        val response = safeApi { discoverMovieApi.discoverMovies() }

        if (response.isSuccess && response.getOrNull() != null && response.getOrThrow().results != null) {
            emit(Domain.Data(response))
        } else {
            val errorMessage = response.getOrNull()?.status_message ?: "Error"
            emit(Domain.Data(Result.failure(Throwable(errorMessage))))
        }

    }.flowOn(IO)

    override fun discoverMoviesFromLocal(): Flow<Domain<DiscoverMovieModel>> =
        dao.getAllResults().map {
            Domain.Data(
                Result.success(
                    DiscoverMovieModel(1,
                        it.map {
                            it.cast()
                        }, 1, 1, 200, "", true
                    )

                )
            )
        }

    override fun saveToLocal(model: ResultModel) {
        CoroutineScope(IO).launch {
            dao.insertNewResult(model.castToEntity())
        }
    }

    override fun deleteFromLocal(model: ResultModel) {
        CoroutineScope(IO).launch {
            dao.deleteAResult(model.id)
        }
    }
}