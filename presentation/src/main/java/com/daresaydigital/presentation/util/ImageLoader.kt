package com.daresaydigital.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object ImageLoader {

    fun load(view: ImageView, url: String?) {
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(view.context)
            .load(url)
//            .placeholder(R.color.dark)
            .apply(requestOptions)
            .centerInside()
            .override(500,500)
//            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}