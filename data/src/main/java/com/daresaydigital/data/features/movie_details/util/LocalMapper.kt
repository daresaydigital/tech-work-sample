package com.daresaydigital.data.features.movie_details.util

import com.daresaydigital.data.features.movie_details.model.*
import com.daresaydigital.domain.features.movie_details.model.*

fun MovieDetailsLocalEntity.toDomainModel() = MovieDetails(
    adult,backdrop_path,belongs_to_collection.toDomainModel(),budget,genres.toDomainArrayModel(),homepage,id,imdb_id,original_language,original_title,overview,popularity,poster_path,
    production_companies.toDomainArrayModel(),production_countries.toDomainArrayModel(),release_date,revenue,runtime,spoken_languages.toDomainArrayModel(),status,tagline,title,video,vote_average,vote_count
)

fun MovieDetails.toLocalModel() = MovieDetailsLocalEntity(
    adult,backdropPath,belongsToCollection.toLocalModel(),budget,genres.toLocalArrayModel(),homepage,id,imdbId,originalLanguage,originalTitle,overview,popularity,posterPath,
    productionCompanies.toLocalArrayModel(),productionCountries.toLocalArrayModel(),releaseDate,revenue,runtime,spokenLanguages.toLocalArrayModel(),status,tagline,title,video,voteAverage,voteCount
)

fun BelongsToCollectionLocal.toDomainModel() = BelongsToCollection(
    backdrop_path,id,name,poster_path
)

fun BelongsToCollection.toLocalModel() = BelongsToCollectionLocal(
    backdropPath,id,name,posterPath
)

fun GenreLocal.toDomainModel() = Genre(
    id,name ?: ""
)

fun Genre.toLocalModel() = GenreLocal(
    id,name
)

@JvmName("toDomainArrayModelGenreLocal")
fun List<GenreLocal>.toDomainArrayModel(): List<Genre> {
    val arrays = mutableListOf<Genre>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelGenre")
fun List<Genre>.toLocalArrayModel(): List<GenreLocal> {
    val arrays = mutableListOf<GenreLocal>()
    this.forEach {
        arrays.add(it.toLocalModel())
    }
    return arrays.toList()
}


fun ProductionCompanyLocal.toDomainModel() = ProductionCompany(
    id,logo_path?:"",name ?: "",origin_country ?: ""
)

fun ProductionCompany.toLocalModel() = ProductionCompanyLocal(
    id,logoPath,name,originCountry
)

@JvmName("toDomainArrayModelProductionCompanyLocal")
fun List<ProductionCompanyLocal>.toDomainArrayModel(): List<ProductionCompany> {
    val arrays = mutableListOf<ProductionCompany>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelProductionCompany")
fun List<ProductionCompany>.toLocalArrayModel(): List<ProductionCompanyLocal> {
    val arrays = mutableListOf<ProductionCompanyLocal>()
    this.forEach {
        arrays.add(it.toLocalModel())
    }
    return arrays.toList()
}

fun ProductionCountryLocal.toDomainModel() = ProductionCountry(
    iso_3166_1 ?: "",name ?: ""
)

fun ProductionCountry.toLocalModel() = ProductionCountryLocal(
    iso_3166_1,name
)

@JvmName("toDomainArrayModelProductionCountryLocal")
fun List<ProductionCountryLocal>.toDomainArrayModel(): List<ProductionCountry> {
    val arrays = mutableListOf<ProductionCountry>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelProductionCountry")
fun List<ProductionCountry>.toLocalArrayModel(): List<ProductionCountryLocal> {
    val arrays = mutableListOf<ProductionCountryLocal>()
    this.forEach {
        arrays.add(it.toLocalModel())
    }
    return arrays.toList()
}

fun SpokenLanguageLocal.toDomainModel() = SpokenLanguage(
    english_name ?: "",iso_639_1 ?: "",name ?: ""
)

fun SpokenLanguage.toLocalModel() = SpokenLanguageLocal(
    englishName,iso_639_1,name
)

@JvmName("toDomainArrayModelSpokenLanguageLocal")
fun List<SpokenLanguageLocal>.toDomainArrayModel(): List<SpokenLanguage> {
    val arrays = mutableListOf<SpokenLanguage>()
    this.forEach {
        arrays.add(it.toDomainModel())
    }
    return arrays.toList()
}

@JvmName("toLocalArrayModelSpokenLanguage")
fun List<SpokenLanguage>.toLocalArrayModel(): List<SpokenLanguageLocal> {
    val arrays = mutableListOf<SpokenLanguageLocal>()
    this.forEach {
        arrays.add(it.toLocalModel())
    }
    return arrays.toList()
}