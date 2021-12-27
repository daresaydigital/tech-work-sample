package com.daresaydigital.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.cancel

/**
 * base view model class that is responsible for clearing threads/coroutines to ensure that
 * there is no memory leak cause of them
 */

open class BaseViewModel : ViewModel() {

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}