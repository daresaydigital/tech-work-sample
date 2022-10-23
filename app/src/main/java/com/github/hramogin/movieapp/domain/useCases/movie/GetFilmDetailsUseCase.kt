package com.github.hramogin.movieapp.domain.useCases.movie

import com.github.hramogin.movieapp.data.repository.movie.MovieRepository
import com.github.hramogin.movieapp.domain.mappers.toDetails
import com.github.hramogin.movieapp.domain.mappers.toFilmDetails
import com.github.hramogin.movieapp.domain.mappers.toMovieDetailsDb
import com.github.hramogin.movieapp.domain.mappers.toMovieReviewDb
import com.github.hramogin.movieapp.domain.mappers.toReview
import com.github.hramogin.movieapp.domain.model.MovieDetails
import com.github.hramogin.movieapp.domain.useCases.base.BaseUseCase
import com.github.hramogin.movieapp.domain.useCases.base.Either
import com.github.hramogin.movieapp.domain.useCases.base.Failure
import java.net.UnknownHostException

class GetFilmDetailsUseCase(private val movieRepository: MovieRepository) :
    BaseUseCase<GetFilmDetailsUseCase.Param, MovieDetails>() {

    override suspend fun run(param: Param): Either<Failure, MovieDetails> {
        return try {
            val filmDetails = movieRepository.getFilmDetailsFromServer(param.movieId).toDetails()
            val reviews =
                movieRepository.getFilmReviewsFromServer(param.movieId).map { it.toReview() }
            movieRepository.setFilmDetailsToDb(filmDetails.toMovieDetailsDb())
            movieRepository.setFilmReviewsToDb(reviews.map { it.toMovieReviewDb() })
            Either.Right(filmDetails.copy(reviews = reviews))
        } catch (exception: Exception) {
            if (exception is UnknownHostException) {
                Either.Right(
                    movieRepository.getFilmDetailsFromDb(param.movieId).toFilmDetails()
                )
            } else {
                Either.Left(onWrapException(exception))
            }
        }
    }

    class Param(val movieId: String)
}