package com.vp.weatherapp.ui.main.paging

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import java.util.*


class PageAdapter(fm: FragmentManager, pages: List<Fragment>?) : FragmentStatePagerAdapter(fm) {

    val pages: List<Fragment>

    init {
        if (pages == null) {
            this.pages = ArrayList()
        } else {
            this.pages = pages
        }
    }

    override fun getItem(position: Int): Fragment {
        return pages[position]
    }

    override fun getCount(): Int {
        return pages.size
    }
}