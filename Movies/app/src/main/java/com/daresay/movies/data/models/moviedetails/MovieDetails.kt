package com.daresay.movies.data.models.moviedetails

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MovieDetails")
data class MovieDetails(
    val adult: Boolean,
    val backdrop_path: String,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String,
    @PrimaryKey
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Float,
    val vote_count: Int,
    val reviews: Reviews
)