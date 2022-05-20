package ir.sass.basedomain.model

sealed class Domain<T> {
    class Progress<T>(val message : String = "Loading") : Domain<T>()
    class Data<T>(val data : kotlin.Result<T>) : Domain<T>()
}