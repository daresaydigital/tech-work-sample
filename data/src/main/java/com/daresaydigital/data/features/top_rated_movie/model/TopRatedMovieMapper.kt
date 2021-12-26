package com.daresaydigital.data.features.top_rated_movie.model

import com.daresaydigital.domain.model.MovieDomain

fun TopRatedMoviesLocalEntity.toDomainModel() = MovieDomain(
    adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count
)

fun MovieDomain.toLocalModel() = TopRatedMoviesLocalEntity(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)