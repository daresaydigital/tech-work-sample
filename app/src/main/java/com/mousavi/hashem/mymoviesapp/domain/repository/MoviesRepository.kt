package com.mousavi.hashem.mymoviesapp.domain.repository

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.model.PageData

interface MoviesRepository {

    suspend fun getPopularMovies(
        language: String,
        page: Int,
    ): Either<PageData, String>

    suspend fun getGenres(): Either<Genres, String>
}