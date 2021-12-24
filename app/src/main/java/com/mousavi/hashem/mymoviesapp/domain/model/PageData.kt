package com.mousavi.hashem.mymoviesapp.domain.model

//-1 indicates dummy data for using as MutableSharedFlow default value
data class PageData(
    val page: Int = -1,
    val movies: MutableList<Movie> = mutableListOf(),
    val totalPages: Int = -1,
    val totalResults: Int = -1,
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
