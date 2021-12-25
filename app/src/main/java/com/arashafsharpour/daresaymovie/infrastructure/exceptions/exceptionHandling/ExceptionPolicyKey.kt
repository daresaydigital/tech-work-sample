package com.arashafsharpour.daresaymovie.infrastructure.exceptions.exceptionHandling

import dagger.MapKey
import kotlin.reflect.KClass

@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ExceptionPolicyKey(val value: KClass<out ExceptionPolicy>)