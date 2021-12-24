package com.mousavi.hashem.mymoviesapp.domain.model

data class PageData(
    val page: Int,
    val movies: MutableList<Movie>,
    val totalPages: Int,
    val totalResults: Int,
)

data class Movie(
    val adult: Boolean = false,
    val backdropPath: String? = null,
    val genreIds: List<String> = emptyList(),
    val id: Int = -1,
    val originalLanguage: String = "",
    val originalTitle: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String = "",
    val video: Boolean = false,
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
)
