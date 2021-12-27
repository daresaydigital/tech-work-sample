package com.daresaydigital.domain.features.top_rated_movie.usecase

import com.daresaydigital.domain.features.top_rated_movie.model.TopRatedMovies
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
) : UseCase<TopRatedMovies, GetTopRatedMoviesParams> {

    override suspend fun execute(params: GetTopRatedMoviesParams?): Result<TopRatedMovies> {
        return repository.getTopRatedMovies(params?.page ?: 1)
    }
}

/**
 * UseCase param for getting top rated movies
 */
data class GetTopRatedMoviesParams(val page: Int = 1) : UseCaseParam()