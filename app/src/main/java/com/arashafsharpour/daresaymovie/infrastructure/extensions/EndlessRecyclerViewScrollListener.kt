package com.arashafsharpour.daresaymovie.infrastructure.extensions

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

private const val STARTING_PAGE_INDEX = 1

abstract class EndlessRecyclerViewScrollListener(
    layoutManager: RecyclerView.LayoutManager,
    private val initialPage: Int = STARTING_PAGE_INDEX,
    visibleThreshold: Int = 10
) : RecyclerView.OnScrollListener() {

    private var visibleThreshold = 10

    private var currentPage = initialPage

    private var previousTotalItemCount = 0

    private var loading = true

    private var alreadyDecreasedPageForFailure = false

    abstract fun onLoadMore(page: Int)

    private var mLayoutManager: RecyclerView.LayoutManager = layoutManager

    init {
        when (layoutManager) {
            is LinearLayoutManager ->
                this.visibleThreshold = visibleThreshold
            is GridLayoutManager ->
                this.visibleThreshold = visibleThreshold * layoutManager.spanCount
            is StaggeredGridLayoutManager ->
                this.visibleThreshold = visibleThreshold * layoutManager.spanCount
        }
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var maxSize = 0
        for (i in lastVisibleItemPositions.indices) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i]
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i]
            }
        }
        return maxSize
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        var lastVisibleItemPosition = 0
        val totalItemCount = mLayoutManager.itemCount

        when (mLayoutManager) {
            is StaggeredGridLayoutManager -> {
                val lastVisibleItemPositions =
                    (mLayoutManager as StaggeredGridLayoutManager).findLastVisibleItemPositions(
                        null
                    )
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            }
            is GridLayoutManager ->
                lastVisibleItemPosition =
                    (mLayoutManager as GridLayoutManager).findLastVisibleItemPosition()
            is LinearLayoutManager ->
                lastVisibleItemPosition =
                    (mLayoutManager as LinearLayoutManager).findLastVisibleItemPosition()
        }

        if (loading && totalItemCount > previousTotalItemCount) {
            loading = false
            previousTotalItemCount = totalItemCount
        }

        if (!loading && lastVisibleItemPosition + visibleThreshold > totalItemCount) {
            onLoadMore(++currentPage)
            alreadyDecreasedPageForFailure = false
            loading = true
        }
    }

    fun resetState() {
        currentPage = initialPage
        previousTotalItemCount = 0
        loading = true
        scrollToTop()
    }

    fun failed() {
        Handler(Looper.getMainLooper()).postDelayed({
            loading = false
        }, 1000)
        if (!alreadyDecreasedPageForFailure) {
            currentPage--
            alreadyDecreasedPageForFailure = true
        }
    }

    fun setCurrentPageNumber(pageNumber: Int) {
        this.currentPage = pageNumber
    }

    private fun scrollToTop() {
        when (mLayoutManager) {
            is LinearLayoutManager ->
                (mLayoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
            is GridLayoutManager ->
                (mLayoutManager as GridLayoutManager).scrollToPositionWithOffset(0, 0)
            is StaggeredGridLayoutManager ->
                (mLayoutManager as StaggeredGridLayoutManager).scrollToPositionWithOffset(0, 0)
        }
    }
}