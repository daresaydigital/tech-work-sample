package com.daresaydigital.data.features.movie_details.model

import androidx.room.TypeConverter

object MovieDetailsDbTypeConverter {

    /// belongsToCollection Mapper
    @TypeConverter
    @JvmStatic
    fun belongsToCollectionLocalToString(belongsToCollectionLocal: BelongsToCollectionLocal?): String? {
        return try {
            val builder = StringBuilder()
            builder.append(belongsToCollectionLocal?.backdrop_path)
            builder.append("*")
            builder.append(belongsToCollectionLocal?.id)
            builder.append("*")
            builder.append(belongsToCollectionLocal?.name)
            builder.append("*")
            builder.append(belongsToCollectionLocal?.poster_path)
            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


    }

    @TypeConverter
    @JvmStatic
    fun stringToBelongsToCollectionLocal(data: String?): BelongsToCollectionLocal? {
        try {
            if (data == null) return null
            var backdrop_path = ""
            var id = -1
            var name = ""
            var poster_path = ""
            data.split("*").forEachIndexed { index, s ->
                when (index) {
                    0 -> {
                        backdrop_path = s
                    }
                    1 -> {
                        id = s.toInt()
                    }
                    2 -> {
                        name = s
                    }
                    3 ->{
                        poster_path = s
                    }
                }
            }
            return BelongsToCollectionLocal(backdrop_path, id, name, poster_path)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    /////////

    //// genreLocal Mapper
    @TypeConverter
    @JvmStatic
    fun genreListToString(genreList: List<GenreLocal>?): String? {
        val builder = StringBuilder()
        genreList?.forEach {
            builder.append(genreLocalToString(it))
            builder.append("@")
        }
        return builder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToGenreList(data: String?): List<GenreLocal>? {
        try {
            if (data == null) return null
            val output = mutableListOf<GenreLocal>()
            data.split("@").forEach {
                stringToGenreLocal(it)?.let {
                    output.add(it)
                }
            }
            return output
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @TypeConverter
    @JvmStatic
    fun genreLocalToString(genreLocal: GenreLocal?): String? {
        return try {
            val builder = StringBuilder()
            builder.append(genreLocal?.id)
            builder.append("*")
            builder.append(genreLocal?.name)
            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


    }

    @TypeConverter
    @JvmStatic
    fun stringToGenreLocal(data: String?): GenreLocal? {
        try {
            if (data == null) return null
            var id = -1
            var name = ""
            data.split("*").forEachIndexed { index, s ->
                when (index) {
                    0 -> {
                        id = s.toInt()
                    }
                    1 -> {
                        name = s
                    }
                }
            }
            return GenreLocal(id, name)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }

    ///////////

    /// spokenLanguageLocal Mapper

    @TypeConverter
    @JvmStatic
    fun spokenLanguageLocalListToString(spokenLanguageLocalList: List<SpokenLanguageLocal>?): String? {
        val builder = StringBuilder()
        spokenLanguageLocalList?.forEach {
            builder.append(spokenLanguageLocalToString(it))
            builder.append("@")
        }
        return builder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToSpokenLanguageLocalList(data: String?): List<SpokenLanguageLocal>? {
        try {
            if (data == null) return null
            val output = mutableListOf<SpokenLanguageLocal>()
            data.split("@").forEach {
                stringToSpokenLanguageLocal(it)?.let {
                    output.add(it)
                }
            }
            return output
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @TypeConverter
    @JvmStatic
    fun spokenLanguageLocalToString(spokenLanguageLocal: SpokenLanguageLocal?): String? {
        return try {
            val builder = StringBuilder()
            builder.append(spokenLanguageLocal?.english_name)
            builder.append("*")
            builder.append(spokenLanguageLocal?.iso_639_1)
            builder.append("*")
            builder.append(spokenLanguageLocal?.name)
            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


    }

    @TypeConverter
    @JvmStatic
    fun stringToSpokenLanguageLocal(data: String?): SpokenLanguageLocal? {
        try {
            if (data == null) return null
            var english_name = ""
            var iso_639_1 = ""
            var name = ""
            data.split("*").forEachIndexed { index, s ->
                when (index) {
                    0 -> {
                        english_name = s
                    }
                    1 -> {
                        iso_639_1 = s
                    }
                    2 -> {
                        name = s
                    }
                }
            }
            return SpokenLanguageLocal(english_name,iso_639_1, name)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
    ///////////////

    /// productionCountryLocal Mapper
    @TypeConverter
    @JvmStatic
    fun productionCountryLocalListToString(productionCountryLocal: List<ProductionCountryLocal>?): String? {
        val builder = StringBuilder()
        productionCountryLocal?.forEach {
            builder.append(productionCountryLocalToString(it))
            builder.append("@")
        }
        return builder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToProductionCountryLocalList(data: String?): List<ProductionCountryLocal>? {
        try {
            if (data == null) return null
            val output = mutableListOf<ProductionCountryLocal>()
            data.split("@").forEach {
                stringToProductionCountryLocal(it)?.let {
                    output.add(it)
                }
            }
            return output
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    @TypeConverter
    @JvmStatic
    fun productionCountryLocalToString(productionCountryLocal: ProductionCountryLocal?): String? {
        return try {
            val builder = StringBuilder()
            builder.append(productionCountryLocal?.iso_3166_1)
            builder.append("*")
            builder.append(productionCountryLocal?.name)
            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


    }

    @TypeConverter
    @JvmStatic
    fun stringToProductionCountryLocal(data: String?): ProductionCountryLocal? {
        try {
            if (data == null) return null
            var iso_3166_1 = ""
            var name = ""
            data.split("*").forEachIndexed { index, s ->
                when (index) {
                    0 -> {
                        iso_3166_1 = s
                    }
                    1 -> {
                        name = s
                    }
                }
            }
            return ProductionCountryLocal(iso_3166_1, name)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
    ////////////////

    /// productionCompany Mapper
    @TypeConverter
    @JvmStatic
    fun productionCompanyLocalListToString(productionCompanyLocal: List<ProductionCompanyLocal>?): String? {
        val builder = StringBuilder()
        productionCompanyLocal?.forEach {
            builder.append(productionCompanyLocalToString(it))
            builder.append("@")
        }
        return builder.toString()
    }

    @TypeConverter
    @JvmStatic
    fun stringToProductionCompanyLocalList(data: String?): List<ProductionCompanyLocal>? {
        try {
            if (data == null) return null
            val output = mutableListOf<ProductionCompanyLocal>()
            data.split("@").forEach {
                stringToProductionCompanyLocal(it)?.let {
                    output.add(it)
                }
            }
            return output
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    @TypeConverter
    @JvmStatic
    fun productionCompanyLocalToString(productionCompanyLocal: ProductionCompanyLocal?): String? {
        return try {
            val builder = StringBuilder()
            builder.append(productionCompanyLocal?.id)
            builder.append("*")
            builder.append(productionCompanyLocal?.logo_path)
            builder.append("*")
            builder.append(productionCompanyLocal?.name)
            builder.append("*")
            builder.append(productionCompanyLocal?.origin_country)
            builder.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }


    }

    @TypeConverter
    @JvmStatic
    fun stringToProductionCompanyLocal(data: String?): ProductionCompanyLocal? {
        try {
            if (data == null) return null
            var id = -1
            var logo_path = ""
            var name = ""
            var origin_country = ""
            data.split("*").forEachIndexed { index, s ->
                when (index) {
                    0 -> {
                        id = s.toInt()
                    }
                    1 -> {
                        logo_path = s
                    }
                    2 -> {
                        name = s
                    }
                    3 -> {
                        origin_country = s
                    }
                }
            }
            return ProductionCompanyLocal(id, logo_path,name,origin_country)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

    }
}