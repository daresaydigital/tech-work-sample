package com.daresaydigital.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.daresaydigital.presentation.R

object ImageLoader {

    fun load(view: ImageView, url: String?, width:Int = 500, height:Int= 500, drawable: Int? = null) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(view.context)
            .load(url)
            .placeholder(drawable ?: R.color.black2)
            .apply(requestOptions)
            .centerInside()
            .override(width,height)
//            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }

    fun loadWithDefaultSize(view: ImageView, url: String?, drawable: Int? = null) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(view.context)
            .load(url)
            .placeholder(drawable ?: R.color.black2)
            .apply(requestOptions)
            .centerInside()
//            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}