package com.arashafsharpour.daresaymovie.features.moviedetail.adapter

import androidx.databinding.ViewDataBinding
import com.arashafsharpour.daresaymovie.BR
import com.arashafsharpour.daresaymovie.R
import com.arashafsharpour.daresaymovie.infrastructure.models.contracts.Cast
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseSimpleListAdapter
import javax.inject.Inject

class CastAdapter @Inject() constructor() :
    BaseSimpleListAdapter<Cast, ViewDataBinding>(Cast.DIFF_CALLBACK) {

    override fun getItemViewType(position: Int): Int = R.layout.item_cast

    override fun getItemVariableId(): Int = BR.cast

    override fun onBindViewHolder(holder: DataBindingViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}