package com.daresaydigital.domain.features.favourite_movie.usecase

import com.daresaydigital.domain.features.favourite_movie.model.FavMovieDomain
import com.daresaydigital.domain.features.favourite_movie.repository.FavouriteMoviesRepository
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import javax.inject.Inject

/**
 * UseCase to unfavour movie
 * @see UseCase
 * @see Result
 */

class UnFavourMovieUseCase @Inject constructor(
    private val repository: FavouriteMoviesRepository
) : UseCase<Unit, UnFavourMovieParams> {

    override suspend fun execute(params: UnFavourMovieParams?): Result<Unit> {
        params?.movieDomain?.let {
            repository.removeFavouriteMovie(it.id)
        }
        return Result.Success(Unit)
    }
}

/**
 * UseCase param for unfavour movie
 */
data class UnFavourMovieParams(val movieDomain: FavMovieDomain) : UseCaseParam()