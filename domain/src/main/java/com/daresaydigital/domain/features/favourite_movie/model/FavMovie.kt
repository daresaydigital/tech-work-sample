package com.daresaydigital.domain.features.favourite_movie.model

data class FavMovie(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)