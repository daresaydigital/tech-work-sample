package com.daresaydigital.presentation.feature.main.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.daresaydigital.core.utils.NetworkConstants
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.util.ImageLoader

class MovieAdapter(private val onClick: (Movie) -> Unit) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback) {

    class MovieViewHolder(itemView: View, val onClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val mainLayout = itemView.findViewById<LinearLayout>(R.id.mainLayout)
        private val ivCover = itemView.findViewById<ImageView>(R.id.ivCover)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

        private var currentMovie: Movie? = null
        init {
            mainLayout.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    currentMovie?.let(onClick)
                }
            }
        }

        fun bind(movie: Movie) {
            currentMovie = movie

            tvTitle.text = movie.title
            ImageLoader.load(ivCover,NetworkConstants.BASE_URL_IMAGE_W500 + movie.posterPath)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)

    }
}

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem.id == newItem.id
    }
}
