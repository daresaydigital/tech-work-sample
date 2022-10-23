package com.github.hramogin.movieapp.data.repository.movie

import com.github.hramogin.movieapp.data.database.model.MovieDb
import com.github.hramogin.movieapp.data.database.model.MovieDetailsDb
import com.github.hramogin.movieapp.data.database.model.MovieReviewsDb
import com.github.hramogin.movieapp.data.model.MovieApi
import com.github.hramogin.movieapp.data.model.MovieDetailsApi
import com.github.hramogin.movieapp.data.model.ReviewApi

class MovieRepositoryImpl(private val movieService: MovieService, private val movieDao: DaoMovie) :
    MovieRepository {
    override suspend fun getFilmsFromServer(): List<MovieApi> {
        return movieService.getFilms().results
    }

    override suspend fun getFilmDetailsFromServer(filId: String): MovieDetailsApi {
        return movieService.getFilmDetails(filId)
    }

    override suspend fun getFilmReviewsFromServer(filId: String): List<ReviewApi> {
        return movieService.getFilmReviews(filId).results
    }

    override suspend fun getFilmsFromDb(): List<MovieDb> {
        return movieDao.getAllMovies()
    }

    override suspend fun setFilmsToDb(films: List<MovieDb>) {
        movieDao.insertAllMovies(films)
    }

    override suspend fun getFilmDetailsFromDb(filmId: String): MovieDetailsDb {
        return movieDao.getMovieDetails(filmId)
    }

    override suspend fun setFilmDetailsToDb(movieDetailsDb: MovieDetailsDb) {
        movieDao.insertMovieDetails(movieDetailsDb)
    }

    override suspend fun getFilmReviewsFromDb(filId: String): List<MovieReviewsDb> {
        return movieDao.getAllMovieReviews()
    }

    override suspend fun setFilmReviewsToDb(reviews: List<MovieReviewsDb>) {
        movieDao.insertAllMovieReviews(reviews)
    }
}