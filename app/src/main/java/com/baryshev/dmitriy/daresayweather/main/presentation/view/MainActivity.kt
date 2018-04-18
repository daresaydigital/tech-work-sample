package com.baryshev.dmitriy.daresayweather.main.presentation.view

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.transition.TransitionManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View.*
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.baryshev.dmitriy.daresayweather.R
import com.baryshev.dmitriy.daresayweather.app
import com.baryshev.dmitriy.daresayweather.main.di.MainComponent
import com.baryshev.dmitriy.daresayweather.main.di.MainModule
import com.baryshev.dmitriy.daresayweather.main.domain.MainData
import com.baryshev.dmitriy.daresayweather.main.presentation.vm.MainVM
import com.baryshev.dmitriy.daresayweather.main.presentation.vm.MainViewModelFactory
import com.baryshev.dmitriy.daresayweather.utils.extensions.hideKeyboard
import com.baryshev.dmitriy.daresayweather.utils.extensions.makeErrorSnackBar
import com.baryshev.dmitriy.daresayweather.utils.extensions.morphIcon
import com.baryshev.dmitriy.daresayweather.utils.extensions.toStringOrEmpty
import com.baryshev.dmitriy.daresayweather.utils.logd
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    companion object {
        const val PERMISSIONS_LOCATION_REQUEST_CODE = 100
    }

    @Inject
    lateinit var viewModelFactory: MainViewModelFactory
    private var mainViewModel: MainVM? = null
    private var mainComponent: MainComponent? = null
    private val mainAdapter: MainAdapter by lazy { MainAdapter() }
    private var searchDrawable: Drawable? = null
    private var errorSnackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initDagger()
        initRecyclerView()
        initViewModel()

        ivSearch.setOnClickListener { mainViewModel?.onSearchClick() }

        ivUpdate.setOnClickListener { mainViewModel?.onUpdateClick() }

        etSearchCity.setOnEditorActionListener(TextView.OnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                mainViewModel?.onSearchAction()
                return@OnEditorActionListener true
            }
            false
        })

        etSearchCity.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mainViewModel?.onSearchTextChanged(s.toStringOrEmpty())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        checkPermissions { mainViewModel?.loadData(forceLoading = savedInstanceState == null) }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_LOCATION_REQUEST_CODE -> {
                if (grantResults.isEmpty()) {
                    makeRequest()
                } else {
                    var isPermissionGranted = true
                    grantResults.forEach {
                        if (it != PackageManager.PERMISSION_GRANTED) {
                            isPermissionGranted = false
                            makeRequest()
                            return
                        }
                    }
                    if (isPermissionGranted) mainViewModel?.loadData()
                }
            }
            else -> {
            }
        }
    }

    override fun onDestroy() {
        mainComponent = null
        super.onDestroy()
    }

    private fun initDagger() {
        mainComponent = app.appComponent?.plusMainComponent(MainModule())
            ?.apply { inject(this@MainActivity) }
    }

    private fun initViewModel() {

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainVM::class.java)

        mainViewModel?.currentWeatherData?.observe(this, Observer<CurrentWeatherViewResult?> {
            handleWeatherResult(it)
        })

        mainViewModel?.forecastData?.observe(this, Observer<ForecastViewResult?> {
            handleForecastResult(it)
        })

        mainViewModel?.searchStateData?.observe(this,
                                                Observer<Boolean?> { handleSearchState(it) })
    }

    private fun handleSearchState(isSearchState: Boolean?) {
        if (isSearchState != null) {
            if (isSearchState) {
                enterSearchMode()
            } else {
                exitSearchMode()
            }
        }
    }

    private fun checkPermissions(executeIfSuccess: () -> Unit) {
        val fineLocationPermission = ContextCompat.checkSelfPermission(this,
                                                                       Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationPermission = ContextCompat.checkSelfPermission(this,
                                                                         Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED || coarseLocationPermission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        } else {
            executeIfSuccess()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
                                          arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                                                  Manifest.permission.ACCESS_COARSE_LOCATION),
                                          PERMISSIONS_LOCATION_REQUEST_CODE)
    }

    private fun enterSearchMode() {
        searchDrawable = ivSearch.morphIcon({ ivSearch.setImageResource(R.drawable.ic_cross) })
        TransitionManager.beginDelayedTransition(mainLayout)
        ivUpdate.setImageResource(R.drawable.ic_my_location)
        tvCity.visibility = INVISIBLE
        with(etSearchCity) {
            visibility = VISIBLE
            requestFocus()
        }
    }

    private fun exitSearchMode() {
        if (searchDrawable is AnimatedVectorDrawableCompat) (searchDrawable as AnimatedVectorDrawableCompat).stop()
        hideKeyboard()
        TransitionManager.beginDelayedTransition(mainLayout)
        ivSearch.setImageResource(R.drawable.ic_search_cross_anim)
        ivUpdate.setImageResource(R.drawable.ic_refresh)
        tvCity.visibility = VISIBLE
        etSearchCity.visibility = GONE
    }

    private fun handleForecastResult(it: ForecastViewResult?) {
        when (it) {
            is ForecastViewResult.Success -> {
                val isLandscape = resources.getBoolean(R.bool.isLandscape)
                rvForecast.post { rvForecast.visibility = if (isLandscape) GONE else VISIBLE }
                mainAdapter.submitList(it.data)
                rvForecast.scheduleLayoutAnimation()
            }
            is ForecastViewResult.Error -> if (errorSnackbar?.isShown != true) {
                errorSnackbar = makeErrorSnackBar(mainLayout,
                                                  it.error,
                                                  {
                                                      errorSnackbar?.dismiss()
                                                      mainViewModel?.loadData()
                                                  }, it.message).apply { show() }
            }
        }
    }

    private fun initRecyclerView() {

        with(rvForecast) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(false)
            layoutAnimation = android.view.animation.AnimationUtils.loadLayoutAnimation(this@MainActivity,
                                                                                        R.anim.rv_layout_anim)
            adapter = mainAdapter
        }
    }

    private fun handleWeatherResult(result: CurrentWeatherViewResult?) {
        when (result) {
            is CurrentWeatherViewResult.Success -> {
                ivError.post { ivError.visibility = GONE }
                showWeatherProgress(false)
                showContentVisibility(true)
                showCurrentWeather(result.data)
            }
            is CurrentWeatherViewResult.Progress -> {
                ivError.post { ivError.visibility = GONE }
                showWeatherProgress(true)
                showContentVisibility(false)
            }
            is CurrentWeatherViewResult.Error -> {
                ivError.post { ivError.visibility = VISIBLE }
                showWeatherProgress(false)
                showContentVisibility(false)
                errorSnackbar = makeErrorSnackBar(mainLayout,
                                                  result.error,
                                                  { mainViewModel?.loadData() },
                                                  result.message).apply { show() }
            }
        }

    }

    private fun showWeatherProgress(isProgress: Boolean) {
        logd("progress $isProgress")

        pbWeather.post { pbWeather.visibility = if (!isProgress) GONE else VISIBLE }
    }

    private fun showContentVisibility(isVisible: Boolean) {
        if (isVisible && errorSnackbar?.isShown == true) errorSnackbar?.dismiss()
        groupCurrentWeather.post { groupCurrentWeather.visibility = if (isVisible) VISIBLE else INVISIBLE }
        rvForecast.post { rvForecast.visibility = if (isVisible) rvForecast.visibility else GONE }
    }

    private fun showCurrentWeather(data: MainData.CurrentWeather) {
        logd("current")
        with(data) {
            tvCity.text = city
            tvTemp.text = temp
            tvCondition.text = condition
            tvTempMin.text = minTemp
            tvTempMax.text = maxTemp
            tvWind.text = wind
            tvPressure.text = pressure
            tvHumidity.text = humidity
            tvAdviceContent.text = advice
            ivCondition.setImageResource(iconCondition)
        }
    }
}
