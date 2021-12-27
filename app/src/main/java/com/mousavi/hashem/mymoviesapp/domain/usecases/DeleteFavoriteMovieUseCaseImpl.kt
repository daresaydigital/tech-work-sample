package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository


class DeleteFavoriteMovieUseCaseImpl(
    private val repository: MoviesRepository,
) : DeleteFavoriteMovieUseCase {

    override suspend operator fun invoke(movie: Movie) {
        repository.deleteFavoriteMovieFromDatabase(movie)
    }
}

interface DeleteFavoriteMovieUseCase {
    suspend operator fun invoke(movie: Movie)
}