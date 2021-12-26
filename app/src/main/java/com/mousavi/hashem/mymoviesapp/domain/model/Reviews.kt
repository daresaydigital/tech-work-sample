package com.mousavi.hashem.mymoviesapp.domain.model


data class Reviews(
    val id: Int = -1,
    val page: Int = -1,
    val reviews: List<Review> = emptyList(),
    val totalPages: Int = -1,
    val totalResults: Int = -1,
)

data class Review(
    val author: String,
    val authorDetails: AuthorDetails,
    val content: String?,
    val createdAt: String?,
    val id: String,
)

data class AuthorDetails(
    val avatarPath: String?,
    val name: String?,
    val rating: Float?,
    val username: String,
)