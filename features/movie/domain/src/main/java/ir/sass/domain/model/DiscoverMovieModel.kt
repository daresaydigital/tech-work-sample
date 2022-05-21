package ir.sass.domain.model


data class DiscoverMovieModel(
    val page: Int?,
    val results: List<ResultModel>?,
    val total_pages: Int?,
    val total_results: Int?,
    val status_code: Int?,
    val status_message: String?,
    val success: Boolean
)

data class ResultModel(
    val adult: Boolean,
    val backdrop_path: String?,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val poster_path_large: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)
