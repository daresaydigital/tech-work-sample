package com.daresaydigital.domain.features.movie_details.usecase

import com.daresaydigital.domain.features.movie_details.model.MovieDetails
import com.daresaydigital.domain.features.movie_details.repository.MovieDetailsRepository
import com.daresaydigital.domain.model.usecase.UseCase
import com.daresaydigital.domain.model.usecase.UseCaseParam
import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * UseCase to get movie details
 * @see UseCase
 * @see Result
 */

class GetMovieDetailsUseCase @Inject constructor(
    private val repository: MovieDetailsRepository
) : UseCase<MovieDetails, MovieDetailsParams> {

    override fun executeStream(params: MovieDetailsParams?): Flow<Result<MovieDetails>> {
        return repository.getMovieDetails(params?.id ?: 0)
    }
}

/**
 * UseCase param for getting movie details
 */
data class MovieDetailsParams(val id: Int) : UseCaseParam()