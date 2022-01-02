package com.daresaydigital.data.features.movie_details.local

import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.data.features.movie_details.model.MovieDetailsLocalEntity
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieDetailsLocalDataSource @Inject constructor(
    private val movieDetailsDao: MovieDetailsDao,
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
    suspend fun getMovieDetailsById(movieId: Int): MovieDetailsLocalEntity? {
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