package com.arashafsharpour.daresaymovie.persistence.repositories.config

import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Config
import com.arashafsharpour.daresaymovie.infrastructure.network.ApiClientBuilder
import com.arashafsharpour.daresaymovie.persistence.BASE_URL_KEY
import com.arashafsharpour.daresaymovie.persistence.CONFIG_KEY
import com.arashafsharpour.daresaymovie.persistence.database.sharedpreference.ISharedPreferencesHelper
import com.google.gson.Gson
import javax.inject.Inject


class ConfigRepository @Inject constructor(
    private val configApi: IConfigApi,
    private val sharedPrefHelper: ISharedPreferencesHelper
    ): IConfigRepository {

    override fun getConfig(): ApiClientBuilder<Config> {
        return ApiClientBuilder { configApi.getConfigurations() }
    }

    override fun saveConfigLocally(config: Config): Boolean {
        sharedPrefHelper.setValue(CONFIG_KEY, Gson().toJson(config))
        sharedPrefHelper.setValue(BASE_URL_KEY, config.images.secureBaseUrl)
        return true
    }

    override fun loadConfigLocally(): Config? {
        sharedPrefHelper.getString(CONFIG_KEY, "").apply {
            return if (this.isNotBlank()) {
                Gson().fromJson(this, Config::class.java)
            } else {
                null
            }
        }
    }
}