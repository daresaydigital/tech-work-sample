package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository

class CheckIfFavoriteUseCaseImpl(
    private val repository: MoviesRepository,
) : CheckIfFavoriteUseCase {
    override suspend operator fun invoke(movie: Movie): Boolean {
        return repository.checkIfFavoriteMovie(movie)
    }

}

interface CheckIfFavoriteUseCase {
    suspend operator fun invoke(movie: Movie): Boolean
}