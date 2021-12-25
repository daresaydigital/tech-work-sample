package com.arashafsharpour.daresaymovie.persistence.repositories.config

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Config
import retrofit2.http.GET

interface IConfigApi {

    @GET("/3/configuration")
    suspend fun getConfigurations(): Config
}