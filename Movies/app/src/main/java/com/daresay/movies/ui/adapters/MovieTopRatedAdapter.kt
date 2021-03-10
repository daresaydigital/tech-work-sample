package com.daresay.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.daresay.movies.data.models.movie.Movie
import com.daresay.movies.databinding.AdapterMovieTopRatedItemBinding
import com.daresay.movies.ui.callbacks.MovieOnClickListener
import com.daresay.movies.utils.getGenreList
import com.daresay.movies.utils.getMoviePosterUrl

class MovieTopRatedAdapter(private val onMovieOnClickListener: MovieOnClickListener) : RecyclerView.Adapter<MovieTopRatedAdapter.MovieHolder>() {
    private var items: ArrayList<Movie> = arrayListOf()

    fun addItems(movies: ArrayList<Movie>) {
        val insertPosition = items.size
        this.items.addAll(movies)
        notifyItemRangeInserted(insertPosition, movies.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieTopRatedAdapter.MovieHolder {
        val binding = AdapterMovieTopRatedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) = holder.bind(items[position])

    inner class MovieHolder(private val binding: AdapterMovieTopRatedItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var movie: Movie

        fun bind(movie: Movie) {
            this.movie = movie

            binding.movie = movie
            binding.movieTags.text = getGenreList(movie.genre_ids).joinToString()

            Glide.with(binding.root)
                    .load(getMoviePosterUrl(movie.poster_path))
                    .transform(CenterCrop(), RoundedCorners(25))
                    .into(binding.movieImage)

            binding.root.setOnClickListener {
                onMovieOnClickListener.onMovieClicked(movie.id)
            }
        }
    }
}