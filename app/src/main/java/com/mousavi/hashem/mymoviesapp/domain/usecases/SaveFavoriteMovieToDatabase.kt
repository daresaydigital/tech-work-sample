package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository

class SaveFavoriteMovieToDatabase(
    private val repository: MoviesRepository
) {

    suspend operator fun invoke(movie: Movie){
        repository.saveToFavoriteMovieDatabase(movie)
    }
}