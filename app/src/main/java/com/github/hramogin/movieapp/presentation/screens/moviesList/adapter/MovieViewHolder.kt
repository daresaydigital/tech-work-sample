package com.github.hramogin.movieapp.presentation.screens.moviesList.adapter

import android.graphics.drawable.Drawable
import android.view.View
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.github.hramogin.movieapp.BuildConfig
import com.github.hramogin.movieapp.R
import com.github.hramogin.movieapp.data.network.GlideApp
import com.github.hramogin.movieapp.databinding.LayoutMovieItemBinding
import com.github.hramogin.movieapp.domain.model.Movie
import com.github.hramogin.movieapp.presentation.base.adapter.BaseViewHolder
import com.github.hramogin.movieapp.presentation.base.toPx

class MovieViewHolder(
    val binding: LayoutMovieItemBinding,
) : BaseViewHolder<Movie, LayoutMovieItemBinding>(binding) {

    override fun onBind(item: Movie?) {
        super.onBind(item)
        item ?: return

        binding.tvMovieTitle.text = item.title
        val requestOptions = RequestOptions().transforms(
            CenterCrop(), RoundedCorners(
                itemView.context.resources.getDimension(
                    R.dimen.base_round_corner
                ).toPx
            )
        )
        GlideApp.with(itemView.context)
            .load(BuildConfig.BASE_IMAGE_URL + item.posterPath)
            .apply(requestOptions)
            .listener(object : RequestListener<Drawable> {
                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.tvMovieTitle.text = item.title
                    binding.tvMovieTitle.visibility = View.VISIBLE
                    binding.vTitleContainer.visibility = View.VISIBLE

                    return false
                }

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    return false
                }
            })
            .into(binding.ivMovieImage)
    }
}