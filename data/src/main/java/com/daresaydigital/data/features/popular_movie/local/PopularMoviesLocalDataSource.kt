package com.daresaydigital.data.features.popular_movie.local

import com.daresaydigital.core.utils.GlobalDispatcher
import com.daresaydigital.data.features.popular_movie.model.PopularMoviesLocalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PopularMoviesLocalDataSource @Inject constructor(
    private val popularMoviesDao: PopularMoviesDao,
    private val globalDispatcher: GlobalDispatcher
) {

    /**
     * It is better to have thread select here because each data provider knows
     * better that which thread is suitable for this data
     * and also it prevent issues in other layers.
     */
    suspend fun insertPopularMovies(popularMoviesLocalEntities: List<PopularMoviesLocalEntity>) {
        return withContext(globalDispatcher.io) {
            popularMoviesDao.insert(popularMoviesLocalEntities)
        }
    }

    @Throws
    suspend fun getAllPopularMovies(): Flow<List<PopularMoviesLocalEntity>?> {
        return withContext(globalDispatcher.io) {
            popularMoviesDao.getAllPopularMoviesStream()
        }
    }

    @Throws
    suspend fun removeAllPopularMovies() {
        return withContext(globalDispatcher.io) {
            popularMoviesDao.deleteAll()
        }
    }
}