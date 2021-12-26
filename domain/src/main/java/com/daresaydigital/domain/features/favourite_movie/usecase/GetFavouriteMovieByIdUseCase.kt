package com.daresaydigital.domain.features.favourite_movie.usecase

import com.daresaydigital.domain.features.favourite_movie.model.FavMovieDomain
import com.daresaydigital.domain.features.favourite_movie.repository.FavouriteMoviesRepository
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import javax.inject.Inject

/**
 * UseCase to favour movie
 * @see UseCase
 * @see Result
 */

class GetFavouriteMovieByIdUseCase @Inject constructor(
    private val repository: FavouriteMoviesRepository
) : UseCase<Unit, GetFavouriteMovieByIdParams> {

    override suspend fun execute(params: GetFavouriteMovieByIdParams?): Result<Unit> {
        params?.movieDomain?.let {
            repository.getFavouriteMovieByNumber(it.id)
        }
        return Result.Success(Unit)
    }
}

/**
 * UseCase param for favour movie
 */
data class GetFavouriteMovieByIdParams(val movieDomain: FavMovieDomain) : UseCaseParam()