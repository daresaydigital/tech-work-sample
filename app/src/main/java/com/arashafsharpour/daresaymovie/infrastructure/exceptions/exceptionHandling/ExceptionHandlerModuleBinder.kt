package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling

import com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling.policy.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@InstallIn(SingletonComponent::class)
@Module
interface ExceptionHandlerModuleBinder {

    @Binds
    @IntoSet
    fun bindBadRequestExceptionPolicy(
        badRequestExceptionPolicy: BadRequestExceptionPolicy
    ): ExceptionPolicy

    @Binds
    @IntoSet
    fun bindResourceNotFoundExceptionPolicy(
        resourceNotFoundExceptionPolicy: ResourceNotFoundExceptionPolicy
    ): ExceptionPolicy

    @Binds
    @IntoSet
    fun bindServerExceptionPolicy(serverExceptionPolicy: ServerExceptionPolicy): ExceptionPolicy

    @Binds
    @IntoSet
    fun bindNetworkTimeoutExceptionPolicy(
        networkTimeoutExceptionPolicy: NetworkTimeoutExceptionPolicy
    ): ExceptionPolicy

    @Binds
    @IntoSet
    fun bindGeneralExceptionPolicy(generalExceptionPolicy: GeneralExceptionPolicy): ExceptionPolicy

    @Binds
    @IntoSet
    fun bindUnProcessableExceptionPolicy(
        unProcessableExceptionPolicy: UnProcessableExceptionPolicy
    ): ExceptionPolicy

    @Binds
    @IntoSet
    fun bindForbiddenAccessPolicy(
        forbiddenAccess: ForbiddenAccess
    ): ExceptionPolicy
}