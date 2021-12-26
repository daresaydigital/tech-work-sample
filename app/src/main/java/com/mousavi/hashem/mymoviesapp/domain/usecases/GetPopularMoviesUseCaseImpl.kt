package com.mousavi.hashem.mymoviesapp.domain.usecases

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository


class GetPopularMoviesUseCaseImpl(
    private val repository: MoviesRepository,
    private var getGenres: GetGenresUseCase
): GetPopularMoviesUseCase {
    override suspend operator fun invoke(language: String, page: Int): Either<PageData, String> {
        return when (val genres = getGenres()) {
            is Either.Success -> {
                return when (val popularMovies = repository.getPopularMovies(language, page)) {
                    is Either.Success -> {
                        popularMovies.data.movies.forEach { movie ->
                            movie.genreNames.addAll(mapGenreIdToName(movie.genreIds, genres.data))
                        }
                        Either.Success(popularMovies.data)
                    }

                    is Either.Error -> {
                        Either.Error(popularMovies.error)
                    }
                }
            }
            is Either.Error -> {
                Either.Error(genres.error)
            }
        }
    }

    private fun mapGenreIdToName(list: List<Int>, genres: Genres): List<String> {
        val genreNames = mutableListOf<String>()
        genres.genres.forEach { genre ->
            list.forEach { id ->
                if (id == genre.id) {
                    genreNames.add(genre.name)
                }
            }
        }
        return genreNames
    }

}

interface GetPopularMoviesUseCase {
    suspend operator fun invoke(language: String, page: Int): Either<PageData, String>
}

