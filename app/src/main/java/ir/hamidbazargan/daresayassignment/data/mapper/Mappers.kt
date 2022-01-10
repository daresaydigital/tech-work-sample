package ir.hamidbazargan.daresayassignment.data.mapper

import ir.hamidbazargan.daresayassignment.data.db.entity.MovieDataBaseEntity
import ir.hamidbazargan.daresayassignment.data.webservice.reponse.MovieResponse
import ir.hamidbazargan.daresayassignment.domain.entity.MovieEntity

fun MovieDataBaseEntity.toMovieEntity(): MovieEntity = MovieEntity(
    id,
    title,
    originalTitle,
    overview,
    originalLanguage,
    releaseDate,
    adult,
    backdropPath,
    popularity,
    posterPath,
    video,
    voteAverage,
    voteCount
)

fun MovieResponse.toMovieEntity(): MovieEntity = MovieEntity(
    id,
    title,
    originalTitle,
    overview,
    originalLanguage,
    releaseDate,
    adult,
    backdropPath,
    popularity,
    posterPath,
    video,
    voteAverage,
    voteCount
)

fun MovieEntity.toMovieDataBaseEntity(): MovieDataBaseEntity = MovieDataBaseEntity(
    id,
    title,
    originalTitle,
    overview,
    originalLanguage,
    releaseDate,
    adult,
    backdropPath,
    popularity,
    posterPath,
    video,
    voteAverage,
    voteCount
)