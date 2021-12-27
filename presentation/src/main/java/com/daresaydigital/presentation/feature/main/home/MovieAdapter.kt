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

class MovieAdapter(private val dataList: MutableList<Movie>, private val onClick: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View, val onClick: (Movie) -> Unit) :
        RecyclerView.ViewHolder(itemView) {

        private val mainLayout = itemView.findViewById<LinearLayout>(R.id.mainLayout)
        private val ivCover = itemView.findViewById<ImageView>(R.id.ivCover)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)

        init {
            mainLayout.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    dataList[adapterPosition].let(onClick)
                }
            }
        }

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            ImageLoader.load(ivCover,NetworkConstants.BASE_URL_IMAGE_W500 + movie.posterPath, 700,  700)
        }
    }

    fun setData(newMovies: List<Movie>) {
        val diffCallback = MovieDiffCallback(dataList, newMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(newMovies)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = dataList[position]
        holder.bind(movie)

    }

    override fun getItemCount(): Int = dataList.size
}

class MovieDiffCallback(private val oldList: List<Movie>, private val newList: List<Movie>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        val (_, value, name) = oldList[oldPosition]
        val (_, value1, name1) = newList[newPosition]

        return name == name1 && value == value1
    }
}
