package com.github.hramogin.movieapp.data.repository.movie

import com.github.hramogin.movieapp.data.database.model.MovieDb
import com.github.hramogin.movieapp.data.database.model.MovieDetailsDb
import com.github.hramogin.movieapp.data.database.model.MovieReviewsDb
import com.github.hramogin.movieapp.data.model.MovieApi
import com.github.hramogin.movieapp.data.model.MovieDetailsApi
import com.github.hramogin.movieapp.data.model.ReviewApi

interface MovieRepository {

    suspend fun getFilmsFromServer(): List<MovieApi>

    suspend fun getFilmsFromDb(): List<MovieDb>

    suspend fun setFilmsToDb(films: List<MovieDb>)

    suspend fun getFilmDetailsFromServer(filId: String): MovieDetailsApi

    suspend fun getFilmDetailsFromDb(filmId: String): MovieDetailsDb

    suspend fun setFilmDetailsToDb(movieDetailsDb: MovieDetailsDb)

    suspend fun getFilmReviewsFromServer(filId: String): List<ReviewApi>

    suspend fun getFilmReviewsFromDb(filId: String): List<MovieReviewsDb>

    suspend fun setFilmReviewsToDb(reviews: List<MovieReviewsDb>)

}