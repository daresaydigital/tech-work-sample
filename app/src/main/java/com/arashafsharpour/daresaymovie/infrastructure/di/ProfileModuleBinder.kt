package com.arashafsharpour.daresaymovie.infrastructure.di

import com.arashafsharpour.daresaymovie.features.profile.infrastracture.IProfileCoordinator
import com.arashafsharpour.daresaymovie.features.profile.infrastracture.ProfileCoordinator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.FlowPreview


@FlowPreview
@InstallIn(SingletonComponent::class)
@Module
interface ProfileModuleBinder {
    @Binds
    fun bindMainCoordinator(
        reviewsCoordinator: ProfileCoordinator
    ): IProfileCoordinator
}