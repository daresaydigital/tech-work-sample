package com.mousavi.hashem.mymoviesapp.domain.model


data class Reviews(
    val id: Int,
    val page: Int,
    val reviewDtos: List<Review>,
    val totalPages: Int,
    val totalResults: Int,
)

data class Review(
    val author: String,
    val authorDetailsDto: AuthorDetails,
    val content: String?,
    val createdAt: String?,
    val id: String,
    val updatedAt: String?,
)

data class AuthorDetails(
    val avatarPath: String?,
    val name: String?,
    val rating: Int?,
    val username: String,
)