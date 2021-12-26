package com.daresaydigital.data.features.popular_movie.model

import com.daresaydigital.domain.model.MovieDomain

fun PopularMoviesLocalEntity.toDomainModel() = MovieDomain(
    adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count
)

fun MovieDomain.toLocalModel() = PopularMoviesLocalEntity(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)

@JvmName("toDomainArrayModelPopularMoviesLocal")
fun List<PopularMoviesLocalEntity>.toDomainArrayModel(): List<MovieDomain> {
    val arrays = mutableListOf<MovieDomain>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelPopularMoviesLocal")
fun List<MovieDomain>.toLocalArrayModel(): List<PopularMoviesLocalEntity> {
    val arrays = mutableListOf<PopularMoviesLocalEntity>()
    this.forEach {
        arrays.add(it.toLocalModel())
    }
    return arrays.toList()
}