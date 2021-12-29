package com.movies.tmdb.data.remote.responses


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.movies.tmdb.other.Constants
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "shopping_items")
data class MovieObject(
    var adult: Boolean = false,
    @PrimaryKey
    var id: Int,
    @SerializedName("original_title")
    var originalTitle: String,
    var overview: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("release_date")
    var releaseDate: String,
    var title: String,
    @SerializedName("vote_average")
    var voteAverage: Float,

    var imageBase64: String?

) : Parcelable {
    fun getPosterThumb(): String = Constants.BASE_FOR_THUMB_IMAGE.plus(posterPath)
    fun getPosterFullImage(): String = Constants.BASE_FOR_FULL_IMAGE.plus(posterPath)

}