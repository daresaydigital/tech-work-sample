package ir.sass.base_data

import ir.sass.base_data.model.toJsonString
import ir.sass.base_data.model.toReal
import org.junit.Test

class MapperTest {
    data class A(val number : Int,val word : String)
    @Test
    fun `check if the mapper can cast class A to jsonString`(){
        val a = A(1,"google")
        val jsonString = toJsonString(a)
        assert(jsonString.equals("{\"number\":1,\"word\":\"google\"}"))
    }

    @Test
    fun `check if the mapper can cast json string to the type of A`(){
        val json = "{\"number\":1,\"word\":\"google\"}"
        val casted = toReal<A>(json)
        assert(casted!!.number == 1 && casted.word == "google")
    }
}