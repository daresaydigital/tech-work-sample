package com.arashafsharpour.daresaymovie.persistence.di

import android.content.Context
import androidx.preference.PreferenceManager
import com.arashafsharpour.daresaymovie.persistence.database.sharedpreference.ISharedPreferencesHelper
import com.arashafsharpour.daresaymovie.persistence.database.sharedpreference.SharedPreferencesHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalStorageModuleProvider {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): ISharedPreferencesHelper {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return SharedPreferencesHelper(
            sharedPreferences
        )
    }

}