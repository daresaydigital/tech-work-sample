package ir.sass.movie.data.model.movie

import ir.sass.domain.model.ResultModel
import ir.sass.shared_data.db.model.ResultEntity

fun ResultEntity.cast() : ResultModel = ResultModel(
    adult,null, listOf(),id,
    original_language,original_title,overview,popularity,
    poster_path,poster_path_large,release_date,title,
    false,vote_average,1
)

fun ResultModel.castToEntity() : ResultEntity = ResultEntity(
    adult,id,original_language,original_title,overview,popularity,
    poster_path,poster_path_large,release_date,title,vote_average,vote_count
)