package com.daresaydigital.domain.features.favourite_movie.usecase

import com.daresaydigital.domain.features.favourite_movie.model.FavMovie
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
) : UseCase<FavMovie?, GetFavouriteMovieByIdParams> {

    override suspend fun execute(params: GetFavouriteMovieByIdParams?): Result<FavMovie?> {
        val result = repository.getFavouriteMovieById(params?.id ?: 0)
        return Result.Success(result)
    }
}

/**
 * UseCase param for favour movie
 */
data class GetFavouriteMovieByIdParams(val id: Int) : UseCaseParam()