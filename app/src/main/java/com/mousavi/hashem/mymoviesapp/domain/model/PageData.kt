package com.mousavi.hashem.mymoviesapp.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//-1 indicates dummy data for using as MutableSharedFlow default value
data class PageData(
    val page: Int = -1,
    val movies: MutableList<Movie> = mutableListOf(),
    val totalPages: Int = -1,
    val totalResults: Int = -1,
)

@Parcelize
data class Movie(
    val backdropPath: String? = null,
    val genreNames: MutableList<String> = arrayListOf(),
    val genreIds: List<Int> = emptyList(),
    val id: Int = -1,
    val overview: String = "",
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val title: String = "",
    val voteAverage: Double = 0.0,
    val voteCount: Int = 0,
) : Parcelable
