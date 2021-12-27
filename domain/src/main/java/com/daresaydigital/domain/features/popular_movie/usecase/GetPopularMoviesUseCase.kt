package com.daresaydigital.domain.features.popular_movie.usecase

import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import com.daresaydigital.domain.features.popular_movie.repository.PopularMoviesRepository
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase to get popular movies
 * @see UseCase
 * @see Result
 */

class GetPopularMoviesUseCase @Inject constructor(
    private val repository: PopularMoviesRepository
) : UseCase<PopularMovies, GetPopularMoviesParams> {

    override suspend fun execute(params: GetPopularMoviesParams?): Result<PopularMovies> {
        return repository.getPopularMovies(params?.page ?: 1)
    }
}

/**
 * UseCase param for getting popular movies
 */
data class GetPopularMoviesParams(val page: Int = 1) : UseCaseParam()