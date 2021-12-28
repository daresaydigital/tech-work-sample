package com.movies.tmdb.data.remote.responses


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReviewObject(
    var id: String,
    var author: String,
    var content: String,
    @SerializedName("created_at")
    var createdAt: String
): Parcelable