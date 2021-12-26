package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository

class GetGenresUseCaseImpl(
    private val repository: MoviesRepository,
) : GetGenresUseCase {

    override suspend operator fun invoke(): Either<Genres, String> {
        return repository.getGenres()
    }
}

interface GetGenresUseCase {
    suspend operator fun invoke(): Either<Genres, String>
}