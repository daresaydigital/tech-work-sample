package com.daresaydigital.data.features.favorite_movie.util

import com.daresaydigital.data.features.favorite_movie.model.FavMovieLocalEntity
import com.daresaydigital.common.model.Movie


fun FavMovieLocalEntity.toDomainModel() = Movie(
    adult,backdrop_path?:"",genre_ids,id,original_language?:"",original_title?:"",overview,popularity,poster_path?:"",release_date,title,video,vote_average?:0.0,vote_count
)

fun Movie.toLocalModel() = FavMovieLocalEntity(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)