package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

@Keep
data class ReviewResults(
    @SerializedName("author")
    var author: String,
    @SerializedName("author_details")
    var authorDetails: AuthorDetails,
    @SerializedName("content")
    var content: String,
    @SerializedName("created_at")
    var createdAt: String,
    @SerializedName("id")
    var id: String,
    @SerializedName("updated_at")
    var updatedAt: String,
    @SerializedName("url")
    var url: String
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ReviewResults>() {
            override fun areItemsTheSame(
                oldItem: ReviewResults,
                newItem: ReviewResults
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: ReviewResults,
                newItem: ReviewResults
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
