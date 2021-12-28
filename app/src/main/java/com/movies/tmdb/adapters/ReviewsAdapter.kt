package com.movies.tmdb.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.movies.tmdb.R
import com.movies.tmdb.data.remote.responses.ReviewObject
import kotlinx.android.synthetic.main.item_review.view.*
import javax.inject.Inject


class ReviewsAdapter@Inject constructor() : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {
    class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<ReviewObject>() {
        override fun areItemsTheSame(oldItem: ReviewObject, newItem: ReviewObject): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewObject, newItem: ReviewObject): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var reviews: List<ReviewObject>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        return ReviewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_review,
                parent,
                false
            )
        )
    }

    private var onItemClickListener: ((ReviewObject) -> Unit)? = null

    fun setOnItemClickListener(listener: (ReviewObject) -> Unit) {
        onItemClickListener = listener
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val review = reviews[position]
        holder.itemView.apply {
            author.text = resources.getString(R.string.author, review.author)
            reviewContent.text = review.content
            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(review)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return reviews.size
    }
}















