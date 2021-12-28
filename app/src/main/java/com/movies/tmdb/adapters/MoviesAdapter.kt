package com.movies.tmdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.movies.tmdb.R
import com.movies.tmdb.data.remote.responses.MovieObject
import com.movies.tmdb.other.Extensions.base64ToBitmap
import kotlinx.android.synthetic.main.item_movie.view.*
import javax.inject.Inject

class MoviesAdapter @Inject constructor(
    private val glide: RequestManager
) : RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {
    class MoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<MovieObject>() {
        override fun areItemsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieObject, newItem: MovieObject): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var movies: List<MovieObject>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        return MoviesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_movie,
                parent,
                false
            )
        )
    }

    private var onItemClickListener: ((MovieObject) -> Unit)? = null

    fun setOnItemClickListener(listener: (MovieObject) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = movies[position]
        holder.itemView.apply {
            if (movie.imageBase64.isNullOrEmpty()) {
                glide.load(movie.getPosterFullImage()).into(movieImage)
            } else {
                movieImage.setImageBitmap(movie.imageBase64!!.base64ToBitmap())
            }

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(movie)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}















