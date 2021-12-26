package com.arashafsharpour.daresaymovie.infrastructure.extensions

import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView

class HorizontalItemDecoration constructor(
    private val spacing: Int,
    private val horizontalPadding: Int = 0
) :
    BaseItemDecoration() {
    override fun setSpacingForDirection(
        outRect: Rect,
        layoutManager: RecyclerView.LayoutManager?,
        position: Int,
        itemCount: Int
    ) {
        outRect.right = if (position == 0) horizontalPadding else spacing
        outRect.left = if (position == itemCount - 1) horizontalPadding else 0
    }
}