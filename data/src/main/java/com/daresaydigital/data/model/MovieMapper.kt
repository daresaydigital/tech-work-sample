package com.daresaydigital.data.model

import com.daresaydigital.domain.model.MovieDomain

fun MovieDTO.toDomainModel() = MovieDomain(
    adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count
)

fun MovieDomain.toDtoModel() = MovieDTO(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)

@JvmName("toDomainArrayModelPopularMoviesDTO")
fun List<MovieDTO>.toDomainArrayModel(): List<MovieDomain> {
    val arrays = mutableListOf<MovieDomain>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelPopularMoviesDTO")
fun List<MovieDomain>.toLocalArrayModel(): List<MovieDTO> {
    val arrays = mutableListOf<MovieDTO>()
    this.forEach {
        arrays.add(it.toDtoModel())
    }
    return arrays.toList()
}