package com.sneha.weatherapp.data.repository

import com.sneha.weatherapp.data.local.db.DatabaseService
import com.sneha.weatherapp.data.model.Dummy
import com.sneha.weatherapp.data.remote.NetworkService
import com.sneha.weatherapp.data.remote.request.DummyRequest
import io.reactivex.Single
import javax.inject.Inject

class DummyRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService
) {

    fun fetchDummy(id: String): Single<List<Dummy>> =
        networkService.doDummyCall(DummyRequest(id)).map { it.data }

}