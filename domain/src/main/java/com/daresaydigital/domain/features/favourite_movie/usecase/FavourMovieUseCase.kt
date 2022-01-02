package com.daresaydigital.domain.features.favourite_movie.usecase

import com.daresaydigital.domain.features.favourite_movie.repository.FavouriteMoviesRepository
import com.daresaydigital.common.model.Movie
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import javax.inject.Inject

/**
 * UseCase to favour movie
 * @see UseCase
 * @see Result
 */

class FavourMovieUseCase @Inject constructor(
    private val repository: FavouriteMoviesRepository
) : UseCase<Unit, FavourMovieParams> {

    override suspend fun execute(params: FavourMovieParams?): Result<Unit> {
        params?.movieDomain?.let {
            repository.insertFavouriteMovie(it)
        }
        return Result.Success(Unit)
    }
}

/**
 * UseCase param for favour movie
 */
data class FavourMovieParams(val movieDomain: Movie) : UseCaseParam()