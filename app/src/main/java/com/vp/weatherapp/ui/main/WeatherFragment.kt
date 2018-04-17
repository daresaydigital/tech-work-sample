package com.vp.weatherapp.ui.main

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.vp.weatherapp.R


class WeatherFragment : Fragment() {

    companion object {

        fun newInstance(): WeatherFragment {
            val args: Bundle = Bundle()
//            args.putSerializable(ARG_CAUGHT, caught)
            val fragment = WeatherFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false) as FrameLayout
    }
}