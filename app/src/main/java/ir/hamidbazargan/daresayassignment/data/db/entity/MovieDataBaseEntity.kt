package ir.hamidbazargan.daresayassignment.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieDataBaseEntity(
    val id: Int,
    var title: String,
    var originalTitle: String,
    var overview: String,
    var originalLanguage: String,
    var releaseDate: String?,
    var adult: Boolean,
    var backdropPath: String?,
    var popularity: Float,
    var posterPath: String?,
    var video: Boolean,
    var voteAverage: Float,
    var voteCount: Int,
    @PrimaryKey(autoGenerate = true) val row: Int? = null
)
