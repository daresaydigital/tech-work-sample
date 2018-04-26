package com.vp.weatherapp.ui.main.paging

import android.app.ActivityManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import com.vp.weatherapp.R
import com.vp.weatherapp.data.local.entity.CityWithForecast
import com.vp.weatherapp.di.Params
import com.vp.weatherapp.ui.initial.InitialActivity
import com.vp.weatherapp.ui.main.MainActivity
import com.vp.weatherapp.ui.main.MainContract
import com.vp.weatherapp.ui.main.paging.indicators.DotIndicator
import com.vp.weatherapp.ui.main.paging.indicators.SelectionIndicator
import com.vp.weatherapp.ui.search.SearchActivity
import com.vp.weatherapp.ui.selection.SelectionActivity
import kotlinx.android.synthetic.main.activity_paging.*
import org.koin.android.ext.android.inject
import java.util.*


abstract class PagingActivity<T> : AppCompatActivity() {

    private val pages = ArrayList<Fragment>()

    private var adapter = PageAdapter(supportFragmentManager, pages)

    var backgroundManager: BackgroundManager? = null

    private var progressIndicator: SelectionIndicator? = null

    private var savedState: Bundle? = null

    private val pageChangeListenerDelegate = object : OnPageChangeListener {
        override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            backgroundManager?.updateBackground(rootView, position, positionOffset)
        }

        override fun onPageSelected(position: Int) {
            progressIndicator?.setSelectedItem(position, true)
        }

        override fun onPageScrollStateChanged(state: Int) {
            //
        }
    }

    protected fun onListReady(entities: List<T>) {
        pages.clear()
        pages.addAll(generatePages(entities))

        adapter = PageAdapter(supportFragmentManager, pages)
        initialiseViewPager(savedState)

        regenerateProgressIndicator()
    }

    protected abstract fun generatePages(entities: List<T>): List<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedState = savedInstanceState
        setContentView(R.layout.activity_paging)

        viewPager!!.addOnPageChangeListener(pageChangeListenerDelegate)

        progressIndicator = DotIndicator(this)

    }

    private fun initialiseViewPager(savedInstanceState: Bundle?) {

        val pageIndex = savedInstanceState?.getInt(KEY_CURRENT_PAGE_INDEX, DEFAULT_CURRENT_PAGE_INDEX)
                ?: DEFAULT_CURRENT_PAGE_INDEX

        viewPager!!.adapter = adapter
        viewPager!!.currentItem = pageIndex

        backgroundManager?.updateBackground(rootView, pageIndex, 0f)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_CURRENT_PAGE_INDEX, viewPager!!.currentItem)
    }

    val currentPage: Fragment
        get() = pages[viewPager!!.currentItem]

    val firstPage: Fragment
        get() = pages[0]

    val lastPage: Fragment
        get() = pages[pages.size - 1]

    val indexOfCurrentPage: Int
        get() = viewPager!!.currentItem


    // ViewPager methods

    fun setPageTransformer(reverseDrawingOrder: Boolean, transformer: ViewPager.PageTransformer) {
        viewPager!!.setPageTransformer(reverseDrawingOrder, transformer)
    }

    fun addPageChangeListener(listener: OnPageChangeListener) {
        viewPager!!.addOnPageChangeListener(listener)
    }

    fun removePageChangeListener(listener: OnPageChangeListener) {
        viewPager!!.removeOnPageChangeListener(listener)
    }

    // pages and navigation methods

    fun getPages(): Collection<Fragment> {
        return Collections.unmodifiableCollection(pages)
    }

    fun getPage(pageIndex: Int): Fragment {
        return pages[pageIndex]
    }

    fun getIndexOfPage(page: Fragment): Int {
        return pages.indexOf(page)
    }

    fun goToPage(pageIndex: Int) {
        viewPager!!.currentItem = pageIndex
    }

    fun goToLastPage() {
        viewPager!!.currentItem = pages.size - 1
    }

    fun goToFirstPage() {
        viewPager!!.currentItem = 0
    }

    fun goToNextPage() {
        val isLastPage = viewPager!!.currentItem == pages.size - 1

        if (!isLastPage) {
            viewPager!!.setCurrentItem(viewPager!!.currentItem + 1, true)
        }
    }

    fun goToPreviousPage() {
        val isFirstPage = viewPager!!.currentItem == 0

        if (!isFirstPage) {
            viewPager!!.setCurrentItem(viewPager!!.currentItem - 1, true)
        }
    }

    fun numberOfPages(): Int {
        return pages.size
    }


    // progress indicator methods

    fun setProgressIndicator(selectionIndicator: SelectionIndicator) {
        if (selectionIndicator !is View) {
            throw IllegalArgumentException(
                    "selectionIndicator must be a subclass of android.view.View")
        }

        progressIndicator = selectionIndicator
        regenerateProgressIndicator()
    }

    private fun regenerateProgressIndicator() {

        progress_indicator_wrapper!!.removeAllViews()

        progressIndicator.let {
            progress_indicator_wrapper!!.addView(it as View)
            it.numberOfItems = pages.size
            it.setSelectedItem(indexOfCurrentPage, false)
        }
    }

    fun getProgressIndicator(): SelectionIndicator? {
        return progressIndicator
    }

    companion object {

        private val TAG = "[PagingActivity]"

        private const val KEY_CURRENT_PAGE_INDEX = "current page index"

        private const val DEFAULT_CURRENT_PAGE_INDEX = 0
    }

}