package com.github.hramogin.movieapp.presentation.screens.moviesList.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.hramogin.movieapp.R

class MovieItemDecorator() : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        if (position == RecyclerView.NO_POSITION) {
            return
        }

        val sideMargin =
            view.context.resources.getDimension(R.dimen.base_margi).toInt()
        val innerMargin = view.context.resources.getDimension(R.dimen.base_margi).toInt() / 2

        if (position % 2 == 0) {
            outRect.set(sideMargin, innerMargin, innerMargin, innerMargin)
        } else {
            outRect.set(innerMargin, innerMargin, sideMargin, innerMargin)
        }

        val adapter = parent.adapter ?: return

        if (position == adapter.itemCount - 1 ||
            position == adapter.itemCount - 2
        ) {
            outRect.bottom = sideMargin * 2
        }
    }
}