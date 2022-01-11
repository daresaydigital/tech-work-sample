package ir.hamidbazargan.daresayassignment.presentation.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import ir.hamidbazargan.daresayassignment.presentation.movielist.popular.PopularFragment
import ir.hamidbazargan.daresayassignment.presentation.movielist.bookmark.BookmarkFragment
import ir.hamidbazargan.daresayassignment.presentation.movielist.toprated.TopRatedFragment

class PagesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> PopularFragment()
            1 -> TopRatedFragment()
            else -> BookmarkFragment()
        }
    }
}