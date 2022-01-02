package com.daresaydigital.data.features.favorite_movie.local

import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.data.features.favorite_movie.model.FavMovieLocalEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMovieLocalDataSource @Inject constructor(
    private val favMovieDao: FavMoviesDao,
    private val globalDispatcher: GlobalDispatcher
) {

    /**
     * It is better to have thread select here because each data provider knows
     * better that which thread is suitable for this data
     * and also it prevent issues in other layers.
     */
    suspend fun insertFavouriteMovie(favMovieLocalEntity: FavMovieLocalEntity) {
        return withContext(globalDispatcher.io) {
            favMovieDao.insert(favMovieLocalEntity)
        }
    }

    @Throws
    suspend fun getFavouriteMovieById(movieId: Int): FavMovieLocalEntity? {
        return withContext(globalDispatcher.io) {
            favMovieDao.getFavoriteMovieById(movieId)
        }
    }

    @Throws
    suspend fun removeFavouriteMovie(id: Int) {
        return withContext(globalDispatcher.io) {
            favMovieDao.delete(id)
        }
    }

    @Throws
    suspend fun getAllFavouriteMovies(): List<FavMovieLocalEntity> {
        return withContext(globalDispatcher.io) {
            favMovieDao.getAllFavoriteMovies()
        }
    }

    @Throws
    fun getAllFavouriteMoviesStream(): Flow<List<FavMovieLocalEntity>> {
        return favMovieDao.getAllFavoriteMoviesStream().flowOn(globalDispatcher.io)
    }
}