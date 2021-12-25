package com.mousavi.hashem.mymoviesapp.presentaion.reviews

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
import com.mousavi.hashem.mymoviesapp.domain.model.Review
import com.mousavi.hashem.util.dateFormat
import com.mousavi.hashem.util.dp
import com.mousavi.hashem.util.showHide


class ReviewsAdapter(
    private var onLoadMoreListener: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_DATA = 1
    }

    private val items = mutableListOf<Review>()

    var isLoading = false
        set(value) {
            field = value
            notifyItemChanged(itemCount)
        }

    var noMoreData = false
    var currentPage = 1

    private val itemDecoration = object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State,
        ) {
            super.getItemOffsets(outRect, view, parent, state)
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

    fun appendData(list: List<Review>, page: Int) {
        if (currentPage == page) return
        currentPage = page
        val currentItemsSize = items.size
        if (list.isNotEmpty()) {
            items.addAll(list)
            val newItemsSize = items.size
            notifyItemRangeChanged(currentItemsSize, newItemsSize - currentItemsSize)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == VIEW_TYPE_LOADING) {
            return LoadingViewHolder(inflater.inflate(R.layout.item_loading, parent, false))
        }

        return ReviewViewHolder(inflater.inflate(R.layout.item_review, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is ReviewViewHolder) {
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

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.tv_name)
        private val imageViewAvatar: ImageView = itemView.findViewById(R.id.iv_avatar)
        private val rateTextView: TextView = itemView.findViewById(R.id.tv_rate)
        private val dateTextView: TextView = itemView.findViewById(R.id.tv_date)
        private val contentTextView: TextView = itemView.findViewById(R.id.tv_content)

        @SuppressLint("SetTextI18n")
        fun bind(position: Int) {
            with(items[position]) {
                nameTextView.text = authorDetails.name ?: ""
                imageViewAvatar.load(authorDetails.avatarPath){
                    placeholder(R.drawable.avatar_place_holder)
                    error(R.drawable.avatar_place_holder)
                }
                rateTextView.text = authorDetails.rating.toString() + "/10"
                rateTextView.showHide(authorDetails.rating != null)
                dateTextView.text = dateFormat(createdAt?.substringBefore("T"))
                contentTextView.text = content
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val loading: View = itemView.findViewById(R.id.loading)
    }

}