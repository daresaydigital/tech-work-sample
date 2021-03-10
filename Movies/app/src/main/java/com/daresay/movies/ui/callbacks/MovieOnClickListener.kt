package com.daresay.movies.ui.callbacks

import com.daresay.movies.data.models.movie.Movie

interface MovieOnClickListener {
    fun onMovieClicked(movieId: Int)
}