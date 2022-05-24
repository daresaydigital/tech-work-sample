package ir.sass.base_ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.sass.base_domain.model.Domain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

/**
 * this is the base class for all viewModels
 * @property _loading is for emitting the loading state
 * @property loading is for collecting the loading state
 * @property _error is for emitting the error messages
 * @property error is for collecting the error messages
 * @property action is a helper function which can run a request and it can
 *  handle if the request fail or not, it will emit an error if something is going wrong
 * @property handleError is a function for emitting error
 * @property loading is a function for changing loading state
 */

open class MotherViewModel : ViewModel() {
    private val _loading: MutableSharedFlow<Boolean> = MutableSharedFlow()
    val loading: SharedFlow<Boolean> = _loading

    private val _error: MutableSharedFlow<String> = MutableSharedFlow()
    val error: SharedFlow<String> = _error

    fun <T> action(result: Flow<Domain<T>>, loading: Boolean, action: (T) -> Unit) {
        viewModelScope.launch {
            result.collect { it ->
                if (loading && it is Domain.Progress) {
                    loading(true)
                } else {
                    loading(false)
                    if (it is Domain.Data) {
                        if (it.data.isSuccess) {
                            it.data.getOrNull()?.let {
                                action.invoke(it)
                            }
                        } else {
                            handleError(it.data.exceptionOrNull() ?: Throwable("Error"))
                        }
                    }
                }
            }
        }
    }

    protected fun handleError(t: Throwable) {
        viewModelScope.launch {
            _error.emit(t.message ?: "Error")
        }
    }

    private fun loading(active: Boolean) {
        viewModelScope.launch {
            _loading.emit(active)
        }
    }

}