package com.arashafsharpour.daresaymovie.persistence.repositories.config

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Config
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder

interface IConfigRepository {
    fun getConfig(): ApiClientBuilder<Config>
    fun saveConfigLocally(config: Config): Boolean
    fun loadConfigLocally(): Config?
}