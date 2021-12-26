package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository

class SaveFavoriteMovieToDatabaseUseCaseImpl(
    private val repository: MoviesRepository
) : SaveFavoriteMovieToDatabaseUseCase{

    override suspend operator fun invoke(movie: Movie){
        repository.saveToFavoriteMovieDatabase(movie)
    }
}

interface SaveFavoriteMovieToDatabaseUseCase {
    suspend operator fun invoke(movie: Movie)
}