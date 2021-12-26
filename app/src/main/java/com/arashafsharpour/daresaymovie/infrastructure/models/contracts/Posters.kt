package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

@Keep
data class Posters(
    @SerializedName("aspect_ratio")
    var aspect_ratio: Float,
    @SerializedName("file_path")
    var filePath: String,
    @SerializedName("height")
    var height: Int,
    @SerializedName("iso_639_1")
    var iso: String?,
    @SerializedName("vote_average")
    var voteAverage: Float,
    @SerializedName("vote_count")
    var voteCount: Int,
    @SerializedName("width")
    var width: Int
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Posters>() {
            override fun areItemsTheSame(
                oldItem: Posters,
                newItem: Posters
            ): Boolean {
                return oldItem.filePath == newItem.filePath
            }

            override fun areContentsTheSame(
                oldItem: Posters,
                newItem: Posters
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
