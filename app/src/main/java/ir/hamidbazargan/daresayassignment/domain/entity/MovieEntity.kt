package ir.hamidbazargan.daresayassignment.domain.entity

data class MovieEntity(
    var id: Int,
    var title: String,
    var originalTitle: String,
    var overview: String,
    var originalLanguage: String,
    var releaseDate: String?,
    var adult: Boolean,
    var backdropPath: String?,
    var popularity: Float,
    var posterPath: String?,
    var video: Boolean,
    var voteAverage: Float,
    var voteCount: Int
)
