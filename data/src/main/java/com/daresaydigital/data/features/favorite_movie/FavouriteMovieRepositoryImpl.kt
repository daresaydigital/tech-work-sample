package com.daresaydigital.data.features.favorite_movie

import com.daresaydigital.data.features.favorite_movie.local.FavoriteMovieLocalDataSource
import com.daresaydigital.data.features.favorite_movie.util.toDomainModel
import com.daresaydigital.data.features.favorite_movie.util.toLocalModel
import com.daresaydigital.domain.features.favourite_movie.model.FavMovieDomain
import com.daresaydigital.domain.features.favourite_movie.repository.FavouriteMoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteMovieRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteMovieLocalDataSource
) : FavouriteMoviesRepository {

    override suspend fun insertFavouriteMovie(favMovie: FavMovieDomain) {
        return localDataSource.insertFavouriteMovie(favMovie.toLocalModel())
    }

    override suspend fun getFavouriteMovieById(id: Int): FavMovieDomain? {
        return localDataSource.getFavouriteMovieById(id)?.toDomainModel()
    }

    override suspend fun removeFavouriteMovie(id: Int) {
        return localDataSource.removeFavouriteMovie(id)
    }

    override suspend fun getAllFavouriteMovies(): List<FavMovieDomain> {
        return localDataSource.getAllFavouriteMovies().map {
            it.toDomainModel()
        }
    }

    override fun getAllFavouriteMoviesStream(): Flow<List<FavMovieDomain>> {
        return localDataSource.getAllFavouriteMoviesStream().map {
            it.map {
                it.toDomainModel()
            }
        }
    }
}