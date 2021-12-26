package com.daresaydigital.domain.features.popular_movie.usecase

import com.daresaydigital.domain.features.popular_movie.repository.PopularMoviesRepository
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import javax.inject.Inject

/**
 * UseCase to get popular movies
 * @see UseCase
 * @see Result
 */

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: PopularMoviesRepository
) : UseCase<Unit, GetPopularMoviesParams> {

    override suspend fun execute(params: GetPopularMoviesParams?): Result<Unit> {
        params?.let {
            repository.getPopularMovies(it.page)
        }
        return Result.Success(Unit)
    }
}

/**
 * UseCase param for getting popular movies
 */
data class GetPopularMoviesParams(val page: Int = 1) : UseCaseParam()