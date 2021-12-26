package com.arashafsharpour.daresaymovie.infrastructure.models.contracts

import androidx.annotation.Keep
import com.fasterxml.jackson.annotation.JsonProperty

@Keep
data class PagingResponse<T>(
    @field:JsonProperty var maximum: Int,
    @field:JsonProperty var resultItems: List<T>
)