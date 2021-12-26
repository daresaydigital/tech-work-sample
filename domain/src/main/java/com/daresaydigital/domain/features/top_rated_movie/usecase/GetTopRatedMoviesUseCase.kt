package com.daresaydigital.domain.features.top_rated_movie.usecase

import com.daresaydigital.domain.features.top_rated_movie.repository.TopRatedMoviesRepository
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import javax.inject.Inject

/**
 * UseCase to get top rated movies
 * @see UseCase
 * @see Result
 */

class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: TopRatedMoviesRepository
) : UseCase<Unit, GetTopRatedMoviesParams> {

    override suspend fun execute(params: GetTopRatedMoviesParams?): Result<Unit> {
        params?.let {
            repository.getTopRatedMovies(it.page)
        }
        return Result.Success(Unit)
    }
}

/**
 * UseCase param for getting top rated movies
 */
data class GetTopRatedMoviesParams(val page: Int = 1) : UseCaseParam()