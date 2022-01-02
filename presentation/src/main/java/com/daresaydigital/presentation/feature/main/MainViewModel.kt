package com.daresaydigital.presentation.feature.main

import com.daresaydigital.common.utils.GlobalDispatcher
import com.daresaydigital.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val globalDispatcher: GlobalDispatcher
): BaseViewModel(){

}