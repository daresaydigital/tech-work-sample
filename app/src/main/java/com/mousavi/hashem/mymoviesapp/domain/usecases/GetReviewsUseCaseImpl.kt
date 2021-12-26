package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.model.Reviews
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository


class GetReviewsUseCaseImpl(
    private val repository: MoviesRepository,
): GetReviewsUseCase {
    override suspend operator fun invoke(movieId:Int, language: String, page: Int): Either<Reviews, String> {
        return when (val reviews = repository.getReviews(movieId, language,page)) {
            is Either.Success -> {
                Either.Success(reviews.data)
            }
            is Either.Error -> {
                Either.Error(reviews.error)
            }
        }
    }
}

interface GetReviewsUseCase {
    suspend operator fun invoke(movieId:Int, language: String, page: Int): Either<Reviews, String>
}

