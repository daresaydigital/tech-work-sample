package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository

class GetGenres(
    private val repository: MoviesRepository,
) {

    suspend operator fun invoke(): Either<Genres, String> {
        return repository.getGenres()
    }
}