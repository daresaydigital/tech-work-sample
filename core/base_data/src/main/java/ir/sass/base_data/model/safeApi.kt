package ir.sass.base_data.model

import ir.sass.basedomain.model.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

fun <Domain , T : Mapper<Domain>> safeApi(req : suspend () -> T  ) : Flow<Result<Domain>> = flow{
    try {
        emit(Result.success(req.invoke().cast()))
    }catch (e : Throwable){
        emit(Result.failure(e))
        // todo complete this one
    }
}
