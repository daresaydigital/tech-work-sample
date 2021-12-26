package com.arashafsharpour.daresaymovie.infrastructure.platform

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<T, VH : BaseViewHolder>
    (diffCallback: DiffUtil.ItemCallback<T>) : ListAdapter<T, VH>(diffCallback) {

    override fun submitList(list: MutableList<T>?) {
        super.submitList(if (list != null) ArrayList<T>(list).toMutableList() else null)
    }

    override fun onViewAttachedToWindow(holder: VH) {
        super.onViewAttachedToWindow(holder)
        holder.onAppear()
    }

    override fun onViewDetachedFromWindow(holder: VH) {
        super.onViewDetachedFromWindow(holder)
        holder.onDisappear()
    }
}