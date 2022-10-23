package com.github.hramogin.movieapp.domain.useCases.movie

import com.github.hramogin.movieapp.data.repository.movie.MovieRepository
import com.github.hramogin.movieapp.domain.mappers.toFilmReview
import com.github.hramogin.movieapp.domain.mappers.toMovieReviewDb
import com.github.hramogin.movieapp.domain.mappers.toReview
import com.github.hramogin.movieapp.domain.model.Review
import com.github.hramogin.movieapp.domain.useCases.base.BaseUseCase
import com.github.hramogin.movieapp.domain.useCases.base.Either
import com.github.hramogin.movieapp.domain.useCases.base.Failure
import java.net.UnknownHostException

class GetFilmReviewsUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<GetFilmReviewsUseCase.Param, List<Review>>() {

    override suspend fun run(param: Param): Either<Failure, List<Review>> {
        return try {
            val reviews =
                movieRepository.getFilmReviewsFromServer(param.filmId).map { it.toReview() }
            movieRepository.setFilmReviewsToDb(reviews.map { it.toMovieReviewDb() })
            Either.Right(reviews)
        } catch (exception: Exception) {
            if (exception is UnknownHostException) {
                Either.Right(
                    movieRepository.getFilmReviewsFromDb(param.filmId).map { it.toFilmReview() })
            } else {
                Either.Left(onWrapException(exception))
            }
        }
    }

    class Param(val filmId: String)
}