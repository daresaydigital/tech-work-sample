package com.daresaydigital.data.features.favorite_movie.util

import com.daresaydigital.data.features.favorite_movie.model.FavMovieLocalEntity
import com.daresaydigital.domain.features.favourite_movie.model.FavMovieDomain


fun FavMovieLocalEntity.toDomainModel() = FavMovieDomain(
    adult,backdrop_path?:"",genre_ids,id,original_language?:"",original_title?:"",overview,popularity,poster_path?:"",release_date,title,video,vote_average?:0.0,vote_count
)

fun FavMovieDomain.toLocalModel() = FavMovieLocalEntity(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,release_date,title,video,voteAverage,voteCount
)