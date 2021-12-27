package com.daresaydigital.data.features.popular_movie.model

import com.daresaydigital.data.model.toDomainArrayModel
import com.daresaydigital.domain.features.popular_movie.model.PopularMovies
import com.daresaydigital.domain.model.Movie

fun PopularMoviesLocalEntity.toDomainModel() = Movie(
    adult,backdrop_path,genre_ids,id,original_language,original_title,overview,popularity,poster_path,release_date,title,video,vote_average,vote_count
)

fun Movie.toLocalModel() = PopularMoviesLocalEntity(
    adult,backdropPath,genreIds,id,originalLanguage,originalTitle,overview,popularity,posterPath,releaseDate,title,video,voteAverage,voteCount
)

@JvmName("toDomainArrayModelPopularMoviesLocal")
fun List<PopularMoviesLocalEntity>.toDomainArrayModel(): List<Movie> {
    val arrays = mutableListOf<Movie>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelPopularMoviesLocal")
fun List<Movie>.toLocalArrayModel(): List<PopularMoviesLocalEntity> {
    val arrays = mutableListOf<PopularMoviesLocalEntity>()
    this.forEach {
        arrays.add(it.toLocalModel())
    }
    return arrays.toList()
}

@JvmName("popularMoviesLocalEntityToPopularMoviesDomain")
fun List<PopularMoviesLocalEntity>.toPopularMoviesDomain(): PopularMovies {
    return PopularMovies(1,this.toDomainArrayModel(),0,0)
}

@JvmName("popularMoviesLocalEntityToPopularMoviesDomain")
fun PopularMoviesDTO.toPopularMoviesDomain(): PopularMovies {
    return PopularMovies(this.page,this.results.toDomainArrayModel(),this.total_pages,this.total_results)
}