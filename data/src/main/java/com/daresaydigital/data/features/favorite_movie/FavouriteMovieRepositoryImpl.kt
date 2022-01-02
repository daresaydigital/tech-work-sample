package com.daresaydigital.data.features.favorite_movie

import com.daresaydigital.data.features.favorite_movie.local.FavoriteMovieLocalDataSource
import com.daresaydigital.data.features.favorite_movie.util.toDomainModel
import com.daresaydigital.data.features.favorite_movie.util.toLocalModel
import com.daresaydigital.domain.model.Result
import com.daresaydigital.domain.features.favourite_movie.repository.FavouriteMoviesRepository
import com.daresaydigital.common.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavouriteMovieRepositoryImpl @Inject constructor(
    private val localDataSource: FavoriteMovieLocalDataSource
) : FavouriteMoviesRepository {

    override suspend fun insertFavouriteMovie(favMovie: Movie) {
        return localDataSource.insertFavouriteMovie(favMovie.toLocalModel())
    }

    override suspend fun getFavouriteMovieById(id: Int): Movie? {
        return localDataSource.getFavouriteMovieById(id)?.toDomainModel()
    }

    override suspend fun removeFavouriteMovie(id: Int) {
        return localDataSource.removeFavouriteMovie(id)
    }

    override suspend fun getAllFavouriteMovies(): List<Movie> {
        return localDataSource.getAllFavouriteMovies().map {
            it.toDomainModel()
        }
    }

    override fun getAllFavouriteMoviesStream(): Flow<Result<List<Movie>>> {
        return localDataSource.getAllFavouriteMoviesStream().map {
            Result.Success(it.map { it.toDomainModel() })
        }
    }
}