package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

@Keep
data class Cast(
    @SerializedName("adult")
    var adult: Boolean,
    @SerializedName("gender")
    var gender: Int?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("known_for_department")
    var knownForDepartment: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("original_name")
    var originalName: String,
    @SerializedName("popularity")
    var popularity: String,
    @SerializedName("profile_path")
    var profilePath: String?,
    @SerializedName("character")
    var character: String,
    @SerializedName("credit_id")
    var creditId: String,
    @SerializedName("order")
    var order: Int
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cast>() {
            override fun areItemsTheSame(
                oldItem: Cast,
                newItem: Cast
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Cast,
                newItem: Cast
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
