package com.daresay.movies.data.models.movie

data class MovieList (
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)