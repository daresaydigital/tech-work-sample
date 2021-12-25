package com.arashafsharpour.daresaymovie.infrastructure.extensions

import androidx.annotation.DimenRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.arashafsharpour.daresaymovie.infrastructure.platform.BaseListAdapter
import com.arashafsharpour.daresaymovie.persistence.BASE_IMAGE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T> RecyclerView.setRecyclerViewData(data: List<T>?) {
    val list = if (data is MutableList<T>) data else data?.toMutableList()
    if (adapter is BaseListAdapter<*, *>) {
        (adapter as BaseListAdapter<T, *>).submitList(list)
    }
}

fun RecyclerView.addHorizontalItemDecoration(
    @DimenRes spacing: Int,
    @DimenRes horizontalPadding: Int
) {
    addItemDecoration(
        HorizontalItemDecoration(
            getDimensionPixelSize(spacing),
            getDimensionPixelSize(horizontalPadding)
        )
    )
}

