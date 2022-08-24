package com.example.movieapp.repositories

import com.example.movieapp.network.MoviesApi
import javax.inject.Inject

class MovieRepository @Inject constructor(private val moviesApi: MoviesApi) {

    fun loadPopularMovies() = moviesApi.getPopularMovies()

    fun loadTopRatedMovies() = moviesApi.getTopRatedMovies()

    fun getMovieDetails(movieId: Int) = moviesApi.getMovieDetails(movieId)
}