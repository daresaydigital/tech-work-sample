package com.arashafsharpour.daresaymovie.features.reviews.adapter

import androidx.databinding.ViewDataBinding
import com.arashafsharpour.daresaymovie.BR
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.ReviewResults
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseSimpleListAdapter
import javax.inject.Inject

class ReviewAdapter @Inject() constructor() :
    BaseSimpleListAdapter<ReviewResults, ViewDataBinding>(ReviewResults.DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = R.layout.item_review

    override fun getItemVariableId(): Int = BR.review

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}