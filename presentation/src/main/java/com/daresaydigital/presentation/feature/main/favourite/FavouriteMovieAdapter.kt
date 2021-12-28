package com.daresaydigital.presentation.feature.main.favourite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.daresaydigital.core.utils.NetworkConstants
import com.daresaydigital.domain.model.Movie
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.util.ImageLoader

class FavouriteMovieAdapter(private val dataList: MutableList<Movie>, private val onFavClicked: (Movie) -> Unit, private val onItemClicked: (Movie) -> Unit) :
    RecyclerView.Adapter<FavouriteMovieAdapter.FavouriteMovieViewHolder>() {

    inner class FavouriteMovieViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val mainLayout = itemView.findViewById<ConstraintLayout>(R.id.mainLayout)
        private val ivCover = itemView.findViewById<ImageView>(R.id.ivCover)
        private val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        private val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        private val tvRate = itemView.findViewById<TextView>(R.id.tvRate)
        private val ibFav = itemView.findViewById<ImageButton>(R.id.ibFav)

        init {
            ibFav.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    dataList[adapterPosition].let(onFavClicked)
                }
            }
            mainLayout.setOnClickListener {
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    dataList[adapterPosition].let(onItemClicked)
                }
            }
        }

        fun bind(movie: Movie) {
            tvTitle.text = movie.title
            tvDescription.text = movie.overview
            tvRate.text = "${itemView.context.getString(R.string.rate)} ${movie.voteAverage}"
            ImageLoader.load(ivCover,
                NetworkConstants.BASE_URL_IMAGE_W500 + movie.posterPath, 500,  500, R.drawable.ic_movie_placeholder)
        }
    }

    fun setData(newMovies: List<Movie>) {
        val diffCallback = MovieDiffCallback(dataList, newMovies)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        dataList.clear()
        dataList.addAll(newMovies)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteMovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favourite_movie_item, parent, false)
        return FavouriteMovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavouriteMovieViewHolder, position: Int) {
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