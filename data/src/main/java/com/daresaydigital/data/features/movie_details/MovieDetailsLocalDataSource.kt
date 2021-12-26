package com.daresaydigital.data.features.movie_details

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.model.entity.MovieDetailsLocalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsLocalDataSource @Inject constructor(
    private val movieDetailsDao: MovieDetailsLocalDao,
    private val globalDispatcher: GlobalDispatcher
) {

    /**
     * It is better to have thread select here because each data provider knows
     * better that which thread is suitable for this data
     * and also it prevent issues in other layers.
     */
    suspend fun insertMovieDetails(movieDetailsLocalEntity: MovieDetailsLocalEntity) {
        return withContext(globalDispatcher.io) {
            movieDetailsDao.insert(movieDetailsLocalEntity)
        }
    }

    @Throws
    suspend fun getMovieDetailsById(movieId: Int): Flow<MovieDetailsLocalEntity?> {
        return withContext(globalDispatcher.io) {
            movieDetailsDao.getMovieDetailsById(movieId)
        }
    }

    @Throws
    suspend fun removeMovieDetails(id: Int) {
        return withContext(globalDispatcher.io) {
            movieDetailsDao.delete(id)
        }
    }
}