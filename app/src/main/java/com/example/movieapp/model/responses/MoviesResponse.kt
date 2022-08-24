package com.example.movieapp.model.responses

import com.example.movieapp.model.Movie
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

// response class for movies list
data class MoviesResponse(
    @SerializedName("page")
    @Expose
    var page: Int,
    @SerializedName("total_results")
    @Expose
    var totalResults: Int,
    @SerializedName("total_pages")
    @Expose
    var totalPages: Int,
    @SerializedName("results")
    @Expose
    val movies: List<Movie>
)
