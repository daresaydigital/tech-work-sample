package com.github.hramogin.movieapp.presentation.screens.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.hramogin.movieapp.presentation.base.viewModel.BaseViewModel

class DetailsViewModel(private val id: String) : BaseViewModel() {

    private val _filmId: MutableLiveData<String> = MutableLiveData()

    val filmId: LiveData<String> = _filmId

    override fun onCreate() {
        super.onCreate()
        _filmId.value = id
    }
}