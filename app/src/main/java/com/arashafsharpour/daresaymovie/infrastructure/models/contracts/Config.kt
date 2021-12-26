package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Keep
@Entity
data class Config(
    @PrimaryKey(autoGenerate = true) var key: Int,
    var images: Images,
    @SerializedName("change_keys")
    var changeKeys: ArrayList<String>
) {
}