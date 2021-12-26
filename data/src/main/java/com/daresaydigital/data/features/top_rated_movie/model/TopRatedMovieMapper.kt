package com.daresaydigital.data.features.top_rated_movie.model

import com.daresaydigital.data.features.popular_movie.model.toDomainArrayModel
import com.daresaydigital.data.model.toDomainArrayModel
import com.daresaydigital.domain.features.top_rated_movie.model.TopRatedMoviesDomain
import com.daresaydigital.domain.model.MovieDomain

fun TopRatedMoviesLocalEntity.toDomainModel() = MovieDomain(
    adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count
)

fun MovieDomain.toLocalModelTopRated() = TopRatedMoviesLocalEntity(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)

@JvmName("toDomainArrayModelTopRatedMoviesLocal")
fun List<TopRatedMoviesLocalEntity>.toDomainArrayModel(): List<MovieDomain> {
    val arrays = mutableListOf<MovieDomain>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelTopRatedMoviesLocal")
fun List<MovieDomain>.toLocalArrayModel(): List<TopRatedMoviesLocalEntity> {
    val arrays = mutableListOf<TopRatedMoviesLocalEntity>()
    this.forEach {
        arrays.add(it.toLocalModelTopRated())
    }
    return arrays.toList()
}

@JvmName("popularMoviesLocalEntityToPopularMoviesDomain")
fun List<TopRatedMoviesLocalEntity>.toTopRatedMoviesDomain(): TopRatedMoviesDomain {
    return TopRatedMoviesDomain(1,this.toDomainArrayModel(),0,0)
}

@JvmName("popularMoviesLocalEntityToPopularMoviesDomain")
fun TopRatedMoviesDTO.toTopRatedMoviesDomain(): TopRatedMoviesDomain {
    return TopRatedMoviesDomain(this.page,this.results.toDomainArrayModel(),this.total_pages,this.total_results)
}