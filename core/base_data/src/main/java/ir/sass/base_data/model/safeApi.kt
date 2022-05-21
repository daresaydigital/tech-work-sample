package ir.sass.base_data.model

import ir.sass.basedomain.model.Mapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <Domain , T : Mapper<Domain>> safeApi(req : suspend () -> T  ) : Result<Domain>  {
    return try {
        val data = req.invoke().cast()
        Result.success(data)
    }catch (e : Throwable){
        Result.failure(e)
    }
}
