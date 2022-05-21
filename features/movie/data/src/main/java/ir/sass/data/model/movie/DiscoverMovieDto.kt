package ir.sass.data.model.movie

import com.google.gson.annotations.SerializedName
import ir.sass.basedomain.model.Mapper
import ir.sass.domain.model.DiscoverMovieModel
import ir.sass.domain.model.ResultModel

data class DiscoverMovieDto(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<ResultDto>?,
    @SerializedName("total_pages")
    val total_pages: Int?,
    @SerializedName("total_results")
    val total_results: Int?,
    @SerializedName("status_code")
    val status_code: Int?,
    @SerializedName("status_message")
    val status_message: String?,
    @SerializedName("success")
    val success: Boolean?
) : Mapper<DiscoverMovieModel>{
    override fun cast(): DiscoverMovieModel = DiscoverMovieModel(
        page,results?.map {
            it.cast()
        },total_pages,total_results,status_code,status_message,success?:true
    )

}

data class ResultDto(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdrop_path: String?,
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("original_language")
    val original_language: String,
    @SerializedName("original_title")
    val original_title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val poster_path: String?,
    @SerializedName("release_date")
    val release_date: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val vote_average: Double,
    @SerializedName("vote_count")
    val vote_count: Int
) : Mapper<ResultModel>{
    override fun cast(): ResultModel = ResultModel(
        adult,backdrop_path,genre_ids,id,original_language,original_title,overview,
        popularity,poster_path,release_date,title,video,vote_average,vote_count
    )

}
