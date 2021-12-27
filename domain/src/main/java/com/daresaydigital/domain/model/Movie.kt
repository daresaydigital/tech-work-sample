package com.daresaydigital.domain.model

import com.daresaydigital.domain.features.favourite_movie.model.FavMovie
import java.io.Serializable

data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
): Serializable

fun Movie.toFavMovie() = FavMovie(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)