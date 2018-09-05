package com.suroid.weatherapp.ui.weathercards

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.suroid.weatherapp.R
import com.suroid.weatherapp.models.CityWeatherEntity
import com.suroid.weatherapp.utils.fadeInImage
import kotlinx.android.synthetic.main.fragment_weather_card.*

class WeatherCardFragment : Fragment() {

    private lateinit var viewModel: WeatherCardViewModel
    private val animationSet = AnimatorSet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(WeatherCardViewModel::class.java)

        arguments?.getParcelable<CityWeatherEntity>(CITY_WEATHER_ENTITY)?.let {
            viewModel.setupWithCity(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather_card, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerViewModelObservers()
        setupProgressAnimation()
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

    private fun setupProgressAnimation() {
        val alpha = ObjectAnimator.ofPropertyValuesHolder(
                progress_bar, PropertyValuesHolder.ofFloat("alpha", 1f, 0f))
        alpha.duration = 1000
        val scaleX = ObjectAnimator.ofPropertyValuesHolder(
                progress_bar, PropertyValuesHolder.ofFloat("scaleX", 0f, 1f))
        val scaleY = ObjectAnimator.ofPropertyValuesHolder(
                progress_bar, PropertyValuesHolder.ofFloat("scaleY", 0f, 1f))
        scaleX.duration = 1000
        scaleY.duration = 1000
        alpha.repeatCount = ObjectAnimator.INFINITE
        scaleX.repeatCount = ObjectAnimator.INFINITE
        scaleY.repeatCount = ObjectAnimator.INFINITE


        animationSet.playTogether(alpha, scaleX, scaleY)
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