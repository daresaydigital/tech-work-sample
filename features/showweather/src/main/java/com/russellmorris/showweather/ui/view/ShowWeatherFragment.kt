package com.russellmorris.showweather.ui.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.russellmorris.extensions.Resource
import com.russellmorris.extensions.parseUtcDate
import com.russellmorris.showweather.R
import com.russellmorris.showweather.injectFeature
import com.russellmorris.showweather.ui.viewmodel.ShowWeatherViewModel
import com.russellmorris.presentation.base.BaseFragment
import com.russellmorris.presentation.base.BaseViewModel
import com.russellmorris.showweather.ui.model.Weather
import kotlinx.android.synthetic.main.fragment_show_weather.*
import org.koin.androidx.viewmodel.ext.viewModel


class ShowWeatherFragment : BaseFragment() {

    private val showWeatherViewModel: ShowWeatherViewModel by viewModel()
    private val args: ShowWeatherFragmentArgs by navArgs()

    override fun getViewModel(): BaseViewModel = showWeatherViewModel

    private val snackBar by lazy {
        Snackbar.make(weatherDetail, "Error", Snackbar.LENGTH_INDEFINITE)
            .setAction("Retry") {
                showWeatherViewModel.getWeatherData(
                    latitude = args.latitude,
                    longitude = args.longitude)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        if (savedInstanceState == null) {
            showWeatherViewModel.getWeatherData(args.latitude, args.longitude)
        }
    }

    override fun onResume() {
        super.onResume()
        showWeatherViewModel.weather.observe(this, Observer { update(it) } )
    }

    private fun update(resource: Resource<Weather>?) {
        resource?.let {
            it.data?.let {
                weatherDetails.text = """${it.city} is ${it.temp}"""
            }
            it.message?.let { snackBar.show() }
        }
    }
}
