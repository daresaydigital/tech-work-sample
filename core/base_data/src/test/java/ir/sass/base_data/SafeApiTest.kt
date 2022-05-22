package ir.sass.base_data

import ir.sass.base_data.test.BaseDataTest
import ir.sass.base_domain.model.Domain
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SafeApiTest : BaseDataTest(){

    @ExperimentalCoroutinesApi
    @Test
    fun `check if the safeApi can return the correct data when the request is success`() = testScope.runTest {
        val data : Domain.Data<Int> = helperSuccess()
        assert(data.data.isSuccess && data.data.getOrThrow() == 1)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `check if the safeApi can return the correct data when the request is not success`() = testScope.runTest{
        val data : Domain.Data<Int> = helperFailed()
        assert(data.data.isFailure && data.data.exceptionOrNull()!!.message!! == "Error")
    }

    suspend fun helperSuccess() : Domain.Data<Int> = Domain.Data(Result.success(1))

    suspend fun helperFailed() : Domain.Data<Int> = Domain.Data(Result.failure(Throwable("Error")))
}