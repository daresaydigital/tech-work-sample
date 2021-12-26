package com.daresaydigital.data.features.top_rated_movie.local

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.features.top_rated_movie.model.TopRatedMoviesLocalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMoviesLocalDataSource @Inject constructor(
    private val topRatedMoviesDao: TopRatedMoviesDao,
    private val globalDispatcher: GlobalDispatcher
) {

    /**
     * It is better to have thread select here because each data provider knows
     * better that which thread is suitable for this data
     * and also it prevent issues in other layers.
     */
    suspend fun insertTopRatedMovies(popularMoviesLocalEntities: List<TopRatedMoviesLocalEntity>) {
        return withContext(globalDispatcher.io) {
            topRatedMoviesDao.insert(popularMoviesLocalEntities)
        }
    }

    @Throws
    suspend fun getAllTopRatedMovies(): Flow<List<TopRatedMoviesLocalEntity>?> {
        return withContext(globalDispatcher.io) {
            topRatedMoviesDao.getAllTopRatedMoviesStream()
        }
    }

    @Throws
    suspend fun removeAllTopRatedMovies() {
        return withContext(globalDispatcher.io) {
            topRatedMoviesDao.deleteAll()
        }
    }
}