package com.mousavi.hashem.mymoviesapp.presentaion.favorite

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mousavi.hashem.mymoviesapp.R
import com.mousavi.hashem.mymoviesapp.domain.model.Movie
import com.mousavi.hashem.util.dp


class FavoriteMoviesAdapter(
    private val onItemClicked: (Movie, View) -> Unit,
) : RecyclerView.Adapter<FavoriteMoviesAdapter.MovieViewHolder>() {

    var items: List<Movie> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private val itemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            super.getItemOffsets(outRect, view, parent, state)
            val spanIndex = (view.layoutParams as GridLayoutManager.LayoutParams).spanIndex
            val dp16 = 16.dp
            if (spanIndex == 0) {
                outRect.left = dp16
                outRect.right = dp16 / 2
            } else {
                outRect.right = dp16
                outRect.left = dp16 / 2
            }
            outRect.top = dp16 / 2
            outRect.bottom = dp16 / 2

        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addItemDecoration(itemDecoration)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeItemDecoration(itemDecoration)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount() = items.size

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.tv_title)
        private val posterImageView: ImageView = itemView.findViewById(R.id.iv_poster)
        private val rateTextView: TextView = itemView.findViewById(R.id.tv_rate)

        fun bind(position: Int) {
            val movie = items[position]
            titleTextView.text = movie.title
            posterImageView.load(movie.posterPath)
            rateTextView.text = movie.voteAverage.toString()

            itemView.setOnClickListener {
                val pos = adapterPosition
                if (pos != RecyclerView.NO_POSITION) {
                    onItemClicked(items[pos], posterImageView)
                }
            }
        }
    }


}