package com.github.hramogin.movieapp.domain.mappers

import com.github.hramogin.movieapp.data.database.model.MovieDb
import com.github.hramogin.movieapp.data.database.model.MovieDetailsDb
import com.github.hramogin.movieapp.data.database.model.MovieReviewsDb
import com.github.hramogin.movieapp.data.model.MovieApi
import com.github.hramogin.movieapp.data.model.MovieDetailsApi
import com.github.hramogin.movieapp.data.model.ReviewApi
import com.github.hramogin.movieapp.domain.model.Movie
import com.github.hramogin.movieapp.domain.model.MovieDetails
import com.github.hramogin.movieapp.domain.model.Review

fun MovieApi.toFilm(): Movie {
    return Movie(
        id = id,
        posterPath = posterPath,
        title = title,
    )
}

fun Movie.toMovieDb(): MovieDb {
    return MovieDb(
        id = id,
        posterPath = posterPath,
        title = title,
    )
}

fun MovieDb.toFilm(): Movie {
    return Movie(
        id = id,
        posterPath = posterPath,
        title = title,
    )
}

fun ReviewApi.toReview(): Review {
    return Review(
        id = id,
        name = author,
        content = content,
        date = createdAt,
    )
}

fun Review.toMovieReviewDb(): MovieReviewsDb {
    return MovieReviewsDb(
        id = id,
        content = content,
        createdAt = date,
        author = name,
    )
}

fun MovieReviewsDb.toFilmReview(): Review {
    return Review(
        id = id,
        content = content,
        date = createdAt,
        name = author
    )
}

fun MovieDetailsDb.toFilmDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        reviews = emptyList(),
        posterPath = posterPath,
        title = title,
        tagline = tagline,
        description = overview,
    )
}

fun MovieDetailsApi.toDetails(): MovieDetails {
    return MovieDetails(
        id = id,
        reviews = emptyList(),
        posterPath = posterPath,
        title = title,
        tagline = tagline,
        description = overview,
    )
}

fun MovieDetails.toMovieDetailsDb(): MovieDetailsDb {
    return MovieDetailsDb(
        id = id,
        posterPath = posterPath,
        title = title,
        overview = description,
        tagline = tagline,
    )
}