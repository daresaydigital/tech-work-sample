package com.mousavi.hashem.mymoviesapp.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.mousavi.hashem.mymoviesapp.domain.model.Genre
import com.mousavi.hashem.mymoviesapp.domain.model.Genres

data class GenresDto(
    @SerializedName("genres")
    val genres: List<GenreDto>,
) {
    fun toGenres(): Genres {
        return Genres(
            genres = genres.map { it.toGenre() }
        )
    }
}

data class GenreDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
){
    fun toGenre(): Genre {
        return Genre(
            id = id,
            name = name
        )
    }
}