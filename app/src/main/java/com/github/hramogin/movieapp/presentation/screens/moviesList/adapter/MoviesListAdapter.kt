package com.github.hramogin.movieapp.presentation.screens.moviesList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.hramogin.movieapp.databinding.LayoutMovieItemBinding
import com.github.hramogin.movieapp.domain.model.Movie
import com.github.hramogin.movieapp.presentation.base.adapter.BaseAdapter
import com.github.hramogin.movieapp.presentation.base.adapter.BaseViewHolder

class MoviesListAdapter(private val movieClickListener: (Movie, View) -> Unit) : BaseAdapter<Movie, LayoutMovieItemBinding>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Movie, LayoutMovieItemBinding> {
        val binding =
            LayoutMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<Movie, LayoutMovieItemBinding>,
        position: Int
    ) {
        super.onBindViewHolder(holder, position)
        if(holder is MovieViewHolder) {
            holder.itemView.setOnClickListener {
                movieClickListener.invoke(items[position], holder.binding.ivMovieImage)
            }
        }
    }
}