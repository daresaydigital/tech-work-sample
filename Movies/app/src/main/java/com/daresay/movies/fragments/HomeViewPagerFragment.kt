package com.daresay.movies.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.daresay.movies.R
import com.daresay.movies.adapters.HomePagerAdapter
import com.daresay.movies.databinding.FragmentHomeViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IndexOutOfBoundsException

class HomeViewPagerFragment : Fragment() {
    private lateinit var binding: FragmentHomeViewPagerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeViewPagerBinding.inflate(inflater, container, false)
        binding.viewPager.adapter = HomePagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabText(position)
        }.attach()

        return binding.root
    }

    private fun getTabIcon(position: Int): Int {
        return when (position) {
            HomePagerAdapter.MOVIES_PAGE_INDEX -> R.drawable.ic_baseline_list_24
            HomePagerAdapter.FAVORITES_PAGE_INDEX -> R.drawable.ic_baseline_favorite_24
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabText(position: Int): String {
        return when (position) {
            HomePagerAdapter.MOVIES_PAGE_INDEX -> getString(R.string.main_bottom_navigation_movies)
            HomePagerAdapter.FAVORITES_PAGE_INDEX -> getString(R.string.main_bottom_navigation_favorites)
            else -> ""
        }
    }
}