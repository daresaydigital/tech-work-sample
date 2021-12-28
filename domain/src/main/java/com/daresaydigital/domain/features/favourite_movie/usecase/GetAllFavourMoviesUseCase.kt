package com.daresaydigital.domain.features.favourite_movie.usecase

import com.daresaydigital.domain.features.favourite_movie.repository.FavouriteMoviesRepository
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

/**
 * UseCase to get all favourite movies
 * @see UseCase
 * @see Result
 */

class GetAllFavourMoviesUseCase @Inject constructor(
    private val repository: FavouriteMoviesRepository
) : UseCase<List<Movie>, GetAllFavourMoviesParams> {

    override suspend fun execute(params: GetAllFavourMoviesParams?): Result<List<Movie>> {
        val movie = repository.getAllFavouriteMovies().filter {
            it != null
        }
        return Result.Success(movie)
    }

    override fun executeStream(params: GetAllFavourMoviesParams?): Flow<Result<List<Movie>>> {
        return repository.getAllFavouriteMoviesStream().filter {
            it != null
        }
    }
}

/**
 * UseCase param for using get all favourite movies by UseCase
 */
data class GetAllFavourMoviesParams(val movieDomain: Movie) : UseCaseParam()