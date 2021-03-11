package com.daresay.movies.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.daresay.movies.data.models.moviedetails.MovieDetails
import com.daresay.movies.databinding.AdapterFavoriteMovieItemBinding
import com.daresay.movies.databinding.AdapterGenreItemBinding
import com.daresay.movies.ui.callbacks.MovieOnClickListener
import com.daresay.movies.utils.getGenreList
import com.daresay.movies.utils.getMoviePosterUrl

class FavoriteMoviesAdapter(private val onMovieOnClickListener: MovieOnClickListener) : RecyclerView.Adapter<FavoriteMoviesAdapter.GenreHolder>() {
    private var items: List<MovieDetails> = emptyList()

    fun setItems(items: List<MovieDetails>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMoviesAdapter.GenreHolder {
        val binding = AdapterFavoriteMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: GenreHolder, position: Int) = holder.bind(items[position])

    inner class GenreHolder(private val binding: AdapterFavoriteMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var movie: MovieDetails

        fun bind(movie: MovieDetails) {
            binding.movieDetails = movie
            binding.movieTags.text = movie.genres.joinToString { it.name }

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