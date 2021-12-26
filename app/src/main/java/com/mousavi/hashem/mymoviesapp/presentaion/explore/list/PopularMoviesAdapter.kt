package com.mousavi.hashem.mymoviesapp.presentaion.explore.list

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


class PopularMoviesAdapter(
    private var onLoadMoreListener: (Int) -> Unit,
    private val onItemClicked: (Movie, View) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_DATA = 1
    }

    private val items = mutableListOf<Movie>()

    var isLoading = false
        set(value) {
            field = value
            notifyItemChanged(itemCount)
        }

    var noMoreData = false
    var currentPage = 0

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
        (recyclerView.layoutManager as? GridLayoutManager)?.let { layoutManager ->
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (getItemViewType(position) == VIEW_TYPE_LOADING) return 2
                    return 1
                }
            }
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeItemDecoration(itemDecoration)
    }

    fun appendData(list: List<Movie>, page: Int, totalPages: Int) {
        if (page == -1) return
        if (currentPage == page) return // if we go to details and back, the viewmodel give redundant data(last list)
        currentPage = page
        val currentItemsSize = items.size
        if (list.isNotEmpty()) {
            items.addAll(list)
            val newItemsSize = items.size
            notifyItemRangeChanged(currentItemsSize, newItemsSize - currentItemsSize)
        }

        if (page >= totalPages){
            noMoreData = true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == VIEW_TYPE_LOADING) {
            return LoadingViewHolder(inflater.inflate(R.layout.item_loading, parent, false))
        }

        return MovieViewHolder(inflater.inflate(R.layout.item_movie, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieViewHolder) {
            holder.bind(position)
        }
        if (!noMoreData && !isLoading && position == itemCount - 1) {
            onLoadMoreListener.invoke(currentPage + 1)
        }
    }


    override fun getItemCount(): Int {
        var dataCount = items.size
        if (isLoading) {
            dataCount++
        }
        return dataCount
    }

    override fun getItemViewType(position: Int): Int {
        val dataCount = items.size

        return if (isLoading) {
            if (position < dataCount) {
                VIEW_TYPE_DATA
            } else {
                VIEW_TYPE_LOADING
            }
        } else {
            VIEW_TYPE_DATA
        }
    }

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

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val loading: View = itemView.findViewById(R.id.loading)
    }

}