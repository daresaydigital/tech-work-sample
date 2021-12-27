package com.daresaydigital.presentation.feature.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.daresaydigital.presentation.R
import com.daresaydigital.presentation.base.BaseFragment
import com.daresaydigital.presentation.feature.main.home.popular_movie.PopularMovieFragment
import com.daresaydigital.presentation.feature.main.home.top_rated.TopRatedMovieFragment
import dagger.hilt.android.AndroidEntryPoint
import com.google.android.material.tabs.TabLayout

import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_home.*


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>(){

    override val viewModel: HomeViewModel by viewModels()

    override fun layoutId(): Int = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = AdapterTabPager(this)
        viewPager.adapter = adapter

        tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                viewPager.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when(position){
                0 -> {
                    tab.text = getString(R.string.popular)
                }
                1 -> {
                    tab.text = getString(R.string.top_rated)
                }
            }
        }.attach()
    }


}

class AdapterTabPager(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PopularMovieFragment()
            }
            else -> {
                TopRatedMovieFragment()
            }
        }
    }
}