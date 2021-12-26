package com.daresaydigital.data.features.movie_details.util

import com.daresaydigital.data.features.movie_details.model.*
import com.daresaydigital.domain.features.movie_details.model.*

fun MovieDetailsDTO.toDomainModel() = MovieDetailsDomain(
    adult,backdrop_path,belongs_to_collection.toDomainModel(),budget,genres.toDomainArrayModel(),homepage,id,imdb_id,original_language,original_title,overview,popularity,poster_path,
    production_companies.toDomainArrayModel(),production_countries.toDomainArrayModel(),release_date,revenue,runtime,spoken_languages.toDomainArrayModel(),status,tagline,title,video,vote_average,vote_count
)

fun MovieDetailsDomain.toRemoteModel() = MovieDetailsDTO(
    adult,backdropPath,belongsToCollection.toRemoteModel(),budget,genres.toRemoteArrayModel(),homepage,id,imdbId,originalLanguage,originalTitle,overview,popularity,posterPath,
    productionCompanies.toRemoteArrayModel(),productionCountries.toRemoteArrayModel(),releaseDate,revenue,runtime,spokenLanguages.toRemoteArrayModel(),status,tagline,title,video,voteAverage,voteCount
)

fun BelongsToCollectionDTO.toDomainModel() = BelongsToCollection(
    backdrop_path,id,name,poster_path
)

fun BelongsToCollection.toRemoteModel() = BelongsToCollectionDTO(
    backdropPath,id,name,posterPath
)

fun GenreDTO.toDomainModel() = Genre(
    id,name
)

fun Genre.toRemoteModel() = GenreDTO(
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

@JvmName("toRemoteArrayModelGenre")
fun List<Genre>.toRemoteArrayModel(): List<GenreDTO> {
    val arrays = mutableListOf<GenreDTO>()
    this.forEach {
        arrays.add(it.toRemoteModel())
    }
    return arrays.toList()
}


fun ProductionCompanyDTO.toDomainModel() = ProductionCompany(
    id,logo_path,name,origin_country
)

fun ProductionCompany.toRemoteModel() = ProductionCompanyDTO(
    id,logoPath,name,originCountry
)

@JvmName("toDomainArrayModelProductionCompanyDTO")
fun List<ProductionCompanyDTO>.toDomainArrayModel(): List<ProductionCompany> {
    val arrays = mutableListOf<ProductionCompany>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toRemoteArrayModelProductionCompany")
fun List<ProductionCompany>.toRemoteArrayModel(): List<ProductionCompanyDTO> {
    val arrays = mutableListOf<ProductionCompanyDTO>()
    this.forEach {
        arrays.add(it.toRemoteModel())
    }
    return arrays.toList()
}

fun ProductionCountryDTO.toDomainModel() = ProductionCountry(
    iso_3166_1,name
)

fun ProductionCountry.toRemoteModel() = ProductionCountryDTO(
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

@JvmName("toRemoteArrayModelProductionCountry")
fun List<ProductionCountry>.toRemoteArrayModel(): List<ProductionCountryDTO> {
    val arrays = mutableListOf<ProductionCountryDTO>()
    this.forEach {
        arrays.add(it.toRemoteModel())
    }
    return arrays.toList()
}

fun SpokenLanguageDTO.toDomainModel() = SpokenLanguage(
    english_name,iso_639_1,name
)

fun SpokenLanguage.toRemoteModel() = SpokenLanguageDTO(
    englishName,iso_639_1,name
)

@JvmName("toDomainArrayModelSpokenLanguageDTO")
fun List<SpokenLanguageDTO>.toDomainArrayModel(): List<SpokenLanguage> {
    val arrays = mutableListOf<SpokenLanguage>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toRemoteArrayModelSpokenLanguage")
fun List<SpokenLanguage>.toRemoteArrayModel(): List<SpokenLanguageDTO> {
    val arrays = mutableListOf<SpokenLanguageDTO>()
    this.forEach {
        arrays.add(it.toRemoteModel())
    }
    return arrays.toList()
}