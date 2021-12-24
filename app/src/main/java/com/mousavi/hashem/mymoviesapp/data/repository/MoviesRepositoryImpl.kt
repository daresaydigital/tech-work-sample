package com.mousavi.hashem.mymoviesapp.data.repository

import com.mousavi.hashem.common.Either
import com.mousavi.hashem.mymoviesapp.data.remote.NetworkDataSource
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.model.PageData
import com.mousavi.hashem.mymoviesapp.domain.repository.MoviesRepository
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val networkDataSource: NetworkDataSource,
    ) : MoviesRepository {

    //Nothing make cachedGenres null, but in future it's maybe the case, so we use atomic version
    private val cachedGenres = AtomicReference<Genres>()

    override suspend fun getPopularMovies(language: String, page: Int): Either<PageData, String> {
        return when (val popularMovies = networkDataSource.getPopularMovies(language, page)) {
            is Either.Success -> {
                Either.Success(popularMovies.data.toPageData())
            }
            is Either.Error -> {
                Either.Error(popularMovies.error)
            }
        }
    }

    override suspend fun getGenres(): Either<Genres, String> {
        val genresCached = cachedGenres.get()
        if (genresCached != null) {
            return Either.Success(genresCached)
        }
        return when (val genres = networkDataSource.getGenres()) {
            is Either.Success -> {
                cachedGenres.set(genres.data.toGenres())
                Either.Success(genres.data.toGenres())
            }
            is Either.Error -> {
                Either.Error(genres.error)
            }
        }
    }
}