package com.daresaydigital.domain.features.movie_details.usecase

import com.daresaydigital.domain.features.movie_details.model.MovieDetails
import com.daresaydigital.domain.features.movie_details.repository.MovieDetailsRepository
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import javax.inject.Inject

/**
 * UseCase to get movie details
 * @see UseCase
 * @see Result
 */

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) : UseCase<Unit, MovieDetailsParams> {

    override suspend fun execute(params: MovieDetailsParams?): Result<Unit> {
        params?.movieDomain?.let {
            repository.getMovieDetails(it.id)
        }
        return Result.Success(Unit)
    }
}

/**
 * UseCase param for getting movie details
 */
data class MovieDetailsParams(val movieDomain: MovieDetails) : UseCaseParam()