package com.github.hramogin.movieapp.data.network

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.github.hramogin.movieapp.BuildConfig

@GlideModule
class MovieAppGlideModule : AppGlideModule() {

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        val memorySize = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(5f)
            .build()
        builder.setMemoryCache(LruResourceCache(memorySize.memoryCacheSize.toLong()))
        builder.setLogLevel(if (BuildConfig.DEBUG) Log.DEBUG else Log.ERROR)
        builder.setDefaultTransitionOptions(
            Drawable::class.java,
            DrawableTransitionOptions.withCrossFade()
        )
        builder.setDefaultRequestOptions(
            RequestOptions().transform(CenterCrop())
        )
    }
}