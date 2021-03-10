package com.daresay.movies.ui.viewmodels

import androidx.lifecycle.ViewModel
import com.daresay.movies.data.api.TmdbRepository
import com.daresay.movies.data.models.authentication.SessionRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthenticationViewModel @Inject constructor(private val repository: TmdbRepository) : ViewModel() {
    val userToken = repository.getUserToken()
    fun createSession(requestBody: SessionRequestBody) = repository.createSession(requestBody = requestBody)
}
