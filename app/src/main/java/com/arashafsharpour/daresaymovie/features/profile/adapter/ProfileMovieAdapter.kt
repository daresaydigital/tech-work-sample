package com.arashafsharpour.daresaymovie.features.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.databinding.ItemMovieBinding
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Movie
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseListAdapter
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseViewHolder
import javax.inject.Inject

class ProfileMovieAdapter @Inject constructor() :
    BaseListAdapter<Movie, ProfileMovieAdapter.ProfileListViewHolder>(Movie.DIFF_CALLBACK) {

    var onItemClicked: ((movieId: String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileListViewHolder = ProfileListViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ProfileListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProfileListViewHolder(
        val binding: ItemMovieBinding
    ) : BaseViewHolder(binding) {
        override val lifecycleRegistry = LifecycleRegistry(this@ProfileListViewHolder)

        init {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        fun bind(item: Movie) {
            binding.apply {
                lifecycleOwner = this@ProfileListViewHolder
                movie = item
                root.setOnClickListener { onItemClicked?.invoke(item.id.toString()) }
                executePendingBindings()
            }
        }
    }
}
