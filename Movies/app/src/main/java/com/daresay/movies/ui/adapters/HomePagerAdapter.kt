package com.daresay.movies.ui.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daresay.movies.ui.fragments.FavoritesFragment
import com.daresay.movies.ui.fragments.MoviesFragment
import java.lang.IndexOutOfBoundsException

class HomePagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragments: Map<Int, () -> Fragment> = mapOf(
        MOVIES_PAGE_INDEX to { MoviesFragment() },
        FAVORITES_PAGE_INDEX to { FavoritesFragment() }
    )

    override fun getItemCount() = tabFragments.size

    override fun createFragment(position: Int): Fragment {
        return tabFragments[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }

    companion object {
        const val MOVIES_PAGE_INDEX = 0
        const val FAVORITES_PAGE_INDEX = 1
    }
}