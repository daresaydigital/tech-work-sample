package com.mousavi.hashem.mymoviesapp.data.remote.dto


import com.google.gson.annotations.SerializedName
import com.mousavi.hashem.mymoviesapp.data.remote.Api
import com.mousavi.hashem.mymoviesapp.domain.model.AuthorDetails
import com.mousavi.hashem.mymoviesapp.domain.model.Review
import com.mousavi.hashem.mymoviesapp.domain.model.Reviews

data class ReviewsDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val reviewDtos: List<ReviewDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int,
) {
    fun toReviews(): Reviews {
        return Reviews(
            id = id,
            page = page,
            reviews = reviewDtos.map { it.toReview() },
            totalPages = totalPages,
            totalResults = totalResults
        )
    }
}

data class ReviewDto(
    @SerializedName("author")
    val author: String,
    @SerializedName("author_details")
    val authorDetailsDto: AuthorDetailsDto,
    @SerializedName("content")
    val content: String,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("id")
    val id: String,
    @SerializedName("updated_at")
    val updatedAt: String?,
    @SerializedName("url")
    val url: String?,
) {
    fun toReview(): Review {
        return Review(
            author = author,
            authorDetails = authorDetailsDto.toAuthorDetails(),
            content = content,
            createdAt = createdAt,
            id = id
        )
    }
}

data class AuthorDetailsDto(
    @SerializedName("avatar_path")
    val avatarPath: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("rating")
    val rating: Int?,
    @SerializedName("username")
    val username: String,
) {
    fun toAuthorDetails(): AuthorDetails {
        return AuthorDetails(
            avatarPath = Api.IMAGE_BASE_URL + avatarPath,
            name = name,
            rating = rating,
            username = username
        )
    }
}