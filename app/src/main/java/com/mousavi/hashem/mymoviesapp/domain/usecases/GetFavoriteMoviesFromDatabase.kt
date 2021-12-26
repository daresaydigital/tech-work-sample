package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteMoviesFromDatabase(
    private val repository: MoviesRepository,
) {
    operator fun invoke(): Flow<List<Movie>> {
        return repository.getFavoriteMoviesFromDatabase()
    }
}