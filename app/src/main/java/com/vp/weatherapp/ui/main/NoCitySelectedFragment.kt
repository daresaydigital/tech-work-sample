package com.vp.weatherapp.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vp.weatherapp.R


class NoCitySelectedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_no_city_selected, container, false)
    }

    companion object {
        fun newInstance(): Fragment {
            return NoCitySelectedFragment()
        }
    }
}