package ir.sass.base_domain.model

/*this is a mapper interface for casting dto objects into domain objects
we can use this interface for other usages too*/

interface Mapper<Cast> {
    fun cast(): Cast
}