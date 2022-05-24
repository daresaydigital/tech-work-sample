package ir.sass.base_data.test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import org.junit.Rule

/*this is the parent class for any unit test that exist on data layers
we can not put this class on test folder because of the framework policy
so I added this class here intentionally*/

open class BaseDataTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = StandardTestDispatcher()

    @ExperimentalCoroutinesApi
    protected val testScope = TestScope(testDispatcher)
}