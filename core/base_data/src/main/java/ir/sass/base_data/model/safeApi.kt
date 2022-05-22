package ir.sass.base_data.model

import ir.sass.basedomain.model.Mapper

/*safe api is a mediator function, it will invoke the service request
right now on catch section it returs the Throwable without any changes
but at furthers steps we can expand this function for having multiple options
for example specific message for any type of error*/

suspend fun <Domain , T : Mapper<Domain>> safeApi(req : suspend () -> T  ) : Result<Domain>  {
    return try {
        val data = req.invoke().cast()
        Result.success(data)
    }catch (e : Throwable){
        Result.failure(e)
    }
}
