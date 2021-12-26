package com.arashafsharpour.daresaymovie.features.movies.adapter

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

open class MoviesListAdapter @Inject constructor() :
    BaseListAdapter<Movie, MoviesListAdapter.MoviesListViewHolder>(Movie.DIFF_CALLBACK) {

    var onItemClicked: ((movieId: String) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesListViewHolder = MoviesListViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_movie,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: MoviesListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MoviesListViewHolder(
        val binding: ItemMovieBinding
    ) : BaseViewHolder(binding) {
        override val lifecycleRegistry = LifecycleRegistry(this@MoviesListViewHolder)

        init {
            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE)
        }

        fun bind(item: Movie) {
            binding.apply {
                lifecycleOwner = this@MoviesListViewHolder
                movie = item
                root.setOnClickListener { onItemClicked?.invoke(item.id.toString()) }
                executePendingBindings()
            }
        }
    }
}