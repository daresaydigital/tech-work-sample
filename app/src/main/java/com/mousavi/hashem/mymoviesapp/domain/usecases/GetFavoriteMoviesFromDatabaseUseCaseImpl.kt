package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMoviesFromDatabaseUseCaseImpl(
    private val repository: MoviesRepository,
): GetFavoriteMoviesFromDatabaseUseCase {
    override operator fun invoke(): Flow<List<Movie>> {
        return repository.getFavoriteMoviesFromDatabase()
    }
}

interface GetFavoriteMoviesFromDatabaseUseCase{
    operator fun invoke(): Flow<List<Movie>>
}