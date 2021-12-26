package com.daresaydigital.data.model.entity

import androidx.room.Entity
import androidx.room.Index
import com.daresaydigital.data.model.*

@Entity(
    tableName = "movie_details",
    indices = [Index("id")]
)
data class MovieDetailsLocalEntity(
    val adult: Boolean,
    val backdrop_path: String,
    val belongs_to_collection: BelongsToCollectionLocal,
    val budget: Int,
    val genres: List<GenreLocal>,
    val homepage: String,
    val id: Int,
    val imdb_id: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val production_companies: List<ProductionCompanyLocal>,
    val production_countries: List<ProductionCountryLocal>,
    val release_date: String,
    val revenue: Int,
    val runtime: Int,
    val spoken_languages: List<SpokenLanguageLocal>,
    val status: String,
    val tagline: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)