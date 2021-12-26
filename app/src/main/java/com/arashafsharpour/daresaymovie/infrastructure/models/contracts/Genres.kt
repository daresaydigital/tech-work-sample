package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import androidx.recyclerview.widget.DiffUtil
import com.google.gson.annotations.SerializedName

@Keep
data class Genres(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String
) {
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Genres>() {
            override fun areItemsTheSame(
                oldItem: Genres,
                newItem: Genres
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: Genres,
                newItem: Genres
            ): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }
        }
    }
}
