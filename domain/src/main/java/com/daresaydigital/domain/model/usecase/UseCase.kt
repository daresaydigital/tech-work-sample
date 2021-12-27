package com.daresaydigital.domain.model.usecase

import com.daresaydigital.domain.model.Result
import kotlinx.coroutines.flow.Flow

/**
 * base interface which will be implemented by
 * domain module UseCases.
 * @param Params a UseCaseParam child classes to provide data for execution
 * @param Type result type domain type
 */
interface UseCase<Type, Params : UseCaseParam> {

    suspend fun execute(params: Params?=null): Result<Type>?{
        return null
    }

    fun executeStream(params: Params?=null): Flow<Result<Type>>?{
        return null
    }
}