package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository

class GetPopularMovies(
    private val repository: MoviesRepository,
) {
    suspend operator fun invoke(language: String, page: Int): Either<PageData, String> {
        return repository.getPopularMovies(language, page)
    }
}