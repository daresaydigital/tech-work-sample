package com.github.hramogin.movieapp.presentation.screens.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.hramogin.movieapp.databinding.DescriptionItemBinding
import com.github.hramogin.movieapp.databinding.ReviewItemBinding
import com.github.hramogin.movieapp.databinding.TitleItemBinding

class DetailsAdapter() : RecyclerView.Adapter<BaseDetailViewHolder>() {

    private val items: MutableList<BaseDetailItem> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseDetailViewHolder {
        return when (viewType) {
            TitleViewHolder.TYPE -> {
                val binding =
                    TitleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                TitleViewHolder(binding)
            }
            DescriptionViewHolder.TYPE -> {
                val binding = DescriptionItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                DescriptionViewHolder(binding)
            }
            ReviewViewHolder.TYPE -> {
                val binding =
                    ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                ReviewViewHolder(binding)
            }
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: BaseDetailViewHolder, position: Int) {
        holder.onBind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    fun setItems(data: List<BaseDetailItem>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}

class TitleViewHolder(private val binding: TitleItemBinding) : BaseDetailViewHolder(binding.root) {

    override fun onBind(data: BaseDetailItem) {
        if (data is TitleItem) {
            binding.tvTitle.text = data.title
        }
    }

    companion object {
        const val TYPE = 0
    }
}

class DescriptionViewHolder(private val binding: DescriptionItemBinding) :
    BaseDetailViewHolder(binding.root) {


    override fun onBind(data: BaseDetailItem) {
        if (data is DescriptionItem) {
            binding.tvDescription.text = data.description
        }
    }

    companion object {
        const val TYPE = 1
    }
}

class ReviewViewHolder(private val binding: ReviewItemBinding) :
    BaseDetailViewHolder(binding.root) {

    override fun onBind(data: BaseDetailItem) {
        if (data is ReviewItem) {
            binding.tvReviewContent.text = data.review.content
            binding.tvReviewerName.text = data.review.name
            binding.tvReviewDate.text = data.review.date
        }
    }

    companion object {
        const val TYPE = 2
    }
}

abstract class BaseDetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    abstract fun onBind(data: BaseDetailItem)
}
