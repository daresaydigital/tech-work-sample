package com.suroid.weatherapp.ui.weathercards

import android.animation.AnimatorSet
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suroid.weatherapp.R
import com.suroid.weatherapp.di.Injectable
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.utils.fadeInImage
import com.suroid.weatherapp.utils.setupProgressAnimation
import kotlinx.android.synthetic.main.fragment_weather_card.*
import javax.inject.Inject

class WeatherCardFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: WeatherCardViewModel
    private val animationSet = AnimatorSet()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherCardViewModel::class.java)

        arguments?.getParcelable<CityWeatherEntity>(CITY_WEATHER_ENTITY)?.let {
            viewModel.setupWithCity(it)
        }

        registerViewModelObservers()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupProgressAnimation(animationSet, progress_bar)
    }

    /**
     * Registering observers for related liveData from viewModel
     */
    private fun registerViewModelObservers() {
        viewModel.temp.observe(this, Observer { temp ->
            temp?.let {
                tv_temp.text = it
            }
        })

        viewModel.weatherTitle.observe(this, Observer { weatherTitle ->
            weatherTitle?.let {
                tv_temp_description.text = it
            }
        })

        viewModel.city.observe(this, Observer { city ->
            city?.let {
                tv_city.text = it
            }
        })

        viewModel.humidity.observe(this, Observer { humidity ->
            humidity?.let {
                tv_humidity_percent.text = it
            }
        })

        viewModel.minMaxTemp.observe(this, Observer { minMaxTemp ->
            minMaxTemp?.let {
                tv_min_max_temp.text = it
            }
        })
        viewModel.wind.observe(this, Observer { wind ->
            wind?.let {
                tv_wind_speed.text = it
            }
        })

        viewModel.icon.observe(this, Observer { icon ->
            icon?.let {
                iv_weather.setImageResource(it)
            }
        })

        viewModel.image.observe(this, Observer { icon ->
            icon?.let {
                iv_background.fadeInImage(it)
            }
        })

        viewModel.loadingStatus.observe(this, Observer { visibility ->
            visibility?.let {
                if (it) {
                    progress_bar.visibility = View.VISIBLE
                    animationSet.start()
                } else {
                    animationSet.cancel()
                    progress_bar.visibility = View.GONE
                }
            }
        })
    }

    companion object {

        private const val CITY_WEATHER_ENTITY = "city_weather_entity"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param cityWeatherEntity [CityWeatherEntity] instance to be passed.
         * @return A new instance of fragment [WeatherCardFragment].
         */
        @JvmStatic
        fun newInstance(cityWeatherEntity: CityWeatherEntity) =
                WeatherCardFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CITY_WEATHER_ENTITY, cityWeatherEntity)
                    }
                }
    }

}