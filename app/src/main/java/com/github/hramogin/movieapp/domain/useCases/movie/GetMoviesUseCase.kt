package com.github.hramogin.movieapp.domain.useCases.movie

import com.github.hramogin.movieapp.data.repository.movie.MovieRepository
import com.github.hramogin.movieapp.domain.mappers.toFilm
import com.github.hramogin.movieapp.domain.mappers.toMovieDb
import com.github.hramogin.movieapp.domain.model.Movie
import com.github.hramogin.movieapp.domain.useCases.base.BaseUseCase
import com.github.hramogin.movieapp.domain.useCases.base.Either
import com.github.hramogin.movieapp.domain.useCases.base.Failure
import java.net.UnknownHostException

open class GetMoviesUseCase(private val moviesRepository: MovieRepository) :
    BaseUseCase<GetMoviesUseCase.Params, List<Movie>>() {

    override suspend fun run(param: Params): Either<Failure, List<Movie>> {
        return try {
            val movies = moviesRepository.getFilmsFromServer().map { it.toFilm() }
            moviesRepository.setFilmsToDb(movies.map { it.toMovieDb() })
            return Either.Right(movies)
        } catch (exception: Exception) {
            if (exception is UnknownHostException) {
                Either.Right(moviesRepository.getFilmsFromDb().map { it.toFilm() })
            } else {
                Either.Left(onWrapException(exception))
            }
        }
    }

    open class Params
}