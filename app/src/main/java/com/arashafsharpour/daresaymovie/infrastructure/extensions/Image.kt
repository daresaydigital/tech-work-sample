package com.arashafsharpour.daresaymovie.infrastructure.extensions

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.persistence.BASE_IMAGE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import de.hdodenhof.circleimageview.CircleImageView


@BindingAdapter("imageUrl")
fun AppCompatImageView.loadImage(url: String?) {
    if (!url.isNullOrEmpty()) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(this.context).load("$BASE_IMAGE_URL/original$url")
            .error(context.getDrawable(R.mipmap.avatar_place_holder))
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
    }
}

@BindingAdapter("loadAvatar")
fun CircleImageView.loadAvatar(url: String?) {
    if (!url.isNullOrEmpty()) {
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        Glide.with(this.context)
            .load("$BASE_IMAGE_URL/original$url")
            .error(context.getDrawable(R.mipmap.avatar_place_holder))
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL).into(this)
    }
}