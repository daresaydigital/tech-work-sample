package com.arashafsharpour.daresaymovie.infrastructure.extensions

object CurrentFragment {
    lateinit var currentClass: Class<*>
    var data: Map<String, Any>? = null

    fun isInitialized() = this::currentClass.isInitialized
}