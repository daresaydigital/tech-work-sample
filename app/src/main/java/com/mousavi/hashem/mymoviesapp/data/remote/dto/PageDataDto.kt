package com.mousavi.hashem.mymoviesapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mousavi.hashem.mymoviesapp.data.remote.Api
import com.mousavi.hashem.mymoviesapp.domain.model.Genres
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.mymoviesapp.domain.model.PageData

data class PageDataDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val movies: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
) {
    fun toPageData(genres: Genres): PageData {
        return PageData(
            page = page,
            movies = movies.map { it.toMovie(genres) }.toMutableList(),
            totalPages = totalPages,
            totalResults = totalResults
        )
    }
}

data class MovieDto(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int,
){
    fun toMovie(genres: Genres): Movie {
        return Movie(
            adult = adult,
            backdropPath = Api.IMAGE_BASE_URL + backdropPath,
            genreIds = mapGenreIdToName(genreIds, genres),
            id = id,
            originalLanguage = originalLanguage,
            originalTitle = originalTitle,
            overview = overview,
            popularity = popularity,
            posterPath = Api.IMAGE_BASE_URL + posterPath,
            releaseDate = releaseDate,
            title = title,
            video = video,
            voteAverage = voteAverage,
            voteCount = voteCount
        )
    }

    private fun mapGenreIdToName(list: List<Int>, genres: Genres): List<String> {
        val genreNames = mutableListOf<String>()

        genres.genres.forEach { genre ->
            list.forEach { id->
                if (id == genre.id){
                    genreNames.add(genre.name)
                }
            }
        }
        return genreNames
    }
}