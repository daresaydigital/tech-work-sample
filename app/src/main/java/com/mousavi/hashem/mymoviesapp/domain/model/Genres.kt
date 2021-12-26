package com.mousavi.hashem.mymoviesapp.domain.model

data class Genres(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)