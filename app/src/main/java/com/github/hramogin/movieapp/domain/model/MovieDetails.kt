package com.github.hramogin.movieapp.domain.model

data class MovieDetails(
    val id: String,
    val reviews: List<Review>,
    val posterPath: String,
    val description: String,
    val title: String,
    val tagline: String,
)