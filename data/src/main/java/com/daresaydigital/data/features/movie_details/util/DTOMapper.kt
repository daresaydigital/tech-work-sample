package com.daresaydigital.data.features.movie_details.util

import com.daresaydigital.data.features.movie_details.model.*
import com.daresaydigital.domain.features.movie_details.model.*

fun MovieDetailsDTO.toDomainModel() = MovieDetails(
    adult,backdrop_path,belongs_to_collection?.toDomainModel(),budget,genres?.toDomainArrayModel(),homepage,id,imdb_id,original_language,original_title,overview,popularity,poster_path,
    production_companies?.toDomainArrayModel(),production_countries?.toDomainArrayModel(),release_date,revenue,runtime,spoken_languages?.toDomainArrayModel(),status,tagline,title,video,vote_average,vote_count
)

fun BelongsToCollectionDTO.toDomainModel() = BelongsToCollection(
    backdrop_path,id,name,poster_path
)

fun GenreDTO.toDomainModel() = Genre(
    id,name
)

@JvmName("toDomainArrayModelGenreDTO")
fun List<GenreDTO>.toDomainArrayModel(): List<Genre> {
    val arrays = mutableListOf<Genre>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}


fun ProductionCompanyDTO.toDomainModel() = ProductionCompany(
    id,logo_path,name,origin_country
)

@JvmName("toDomainArrayModelProductionCompanyDTO")
fun List<ProductionCompanyDTO>.toDomainArrayModel(): List<ProductionCompany> {
    val arrays = mutableListOf<ProductionCompany>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

fun ProductionCountryDTO.toDomainModel() = ProductionCountry(
    iso_3166_1,name
)

@JvmName("toDomainArrayModelProductionCountryDTO")
fun List<ProductionCountryDTO>.toDomainArrayModel(): List<ProductionCountry> {
    val arrays = mutableListOf<ProductionCountry>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

fun SpokenLanguageDTO.toDomainModel() = SpokenLanguage(
    english_name,iso_639_1,name
)

@JvmName("toDomainArrayModelSpokenLanguageDTO")
fun List<SpokenLanguageDTO>.toDomainArrayModel(): List<SpokenLanguage> {
    val arrays = mutableListOf<SpokenLanguage>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}