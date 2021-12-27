package com.daresaydigital.data.features.top_rated_movie.model

import com.daresaydigital.data.model.toDomainArrayModel
import com.daresaydigital.domain.features.top_rated_movie.model.TopRatedMovies
import com.daresaydigital.domain.model.Movie

fun TopRatedMoviesLocalEntity.toDomainModel() = Movie(
    adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count
)

fun Movie.toLocalModelTopRated() = TopRatedMoviesLocalEntity(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)

@JvmName("toDomainArrayModelTopRatedMoviesLocal")
fun List<TopRatedMoviesLocalEntity>.toDomainArrayModel(): List<Movie> {
    val arrays = mutableListOf<Movie>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelTopRatedMoviesLocal")
fun List<Movie>.toLocalArrayModel(): List<TopRatedMoviesLocalEntity> {
    val arrays = mutableListOf<TopRatedMoviesLocalEntity>()
    this.forEach {
        arrays.add(it.toLocalModelTopRated())
    }
    return arrays.toList()
}

@JvmName("popularMoviesLocalEntityToPopularMoviesDomain")
fun List<TopRatedMoviesLocalEntity>.toTopRatedMoviesDomain(): TopRatedMovies {
    return TopRatedMovies(1,this.toDomainArrayModel(),0,0)
}

@JvmName("popularMoviesLocalEntityToPopularMoviesDomain")
fun TopRatedMoviesDTO.toTopRatedMoviesDomain(): TopRatedMovies {
    return TopRatedMovies(this.page,this.results.toDomainArrayModel(),this.total_pages,this.total_results)
}