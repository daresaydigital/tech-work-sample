package ir.hamidbazargan.daresayassignment.presentation.movielist

import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

interface ClickListener {
    fun onMovieClick(movie: MovieEntity)
}