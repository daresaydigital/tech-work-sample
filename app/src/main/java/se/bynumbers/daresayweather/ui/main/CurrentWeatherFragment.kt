package se.bynumbers.daresayweather.ui.main

import android.Manifest
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_current_weather.*

import se.bynumbers.daresayweather.model.LocationListener
import se.bynumbers.daresayweather.util.DateTimeHelper
import se.bynumbers.daresayweather.util.LocationHelper
import se.bynumbers.daresayweather.util.PermissionStatus
import se.bynumbers.daresayweather.util.ResourceHelper
import se.bynumbers.daresayweather.R


/**
 * A placeholder fragment containing a simple view.
 */
class CurrentWeatherFragment : Fragment() {

    private lateinit var pageViewModel: PageCurrentWeatherViewModel
    private lateinit var locationDataListener : LocationListener
    private lateinit var currentWeatherViewModel : CurrentWeatherViewModel
    private var alertDialog: AlertDialog? = null
    private val TAG = "CurrentWeatherFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        locationDataListener = ViewModelProvider(this).get(LocationListener::class.java)
        pageViewModel = ViewModelProvider(this).get(PageCurrentWeatherViewModel::class.java)
        currentWeatherViewModel = ViewModelProvider(this).get(CurrentWeatherViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_current_weather, container, false)
        val statusTextView = root.findViewById<TextView>(R.id.status_label)
        val currentTextView = root.findViewById<TextView>(R.id.current_label)
        var mainImageView   = root.findViewById<ImageView>(R.id.main_weather_image)
        val temperatureView = root.findViewById<TextView>(R.id.temperature_label)
        val unitSwitch = root.findViewById<Switch>(R.id.unit_switch)
        val locationSwitch =  root.findViewById<Switch>(R.id.location_switch)
        val btnRetry = root.findViewById<ImageButton>(R.id.btn_retry)

        btnRetry.setVisibility(View.INVISIBLE)
        mainImageView.setImageDrawable(requireContext().getDrawable(R.drawable.wi_na))
        temperatureView.text = ""
        statusTextView.text = getString(R.string.no_location_check)
        btnRetry.setOnClickListener {
            if(LocationHelper.checkLocationPermission(requireContext())){

                    startLocationUpdates()
                    updateLocationUI(true)
            }

        }
        locationSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                if(askForLocationPermission(requireActivity())==1) {
                    startLocationUpdates()
                }
            } else {
                stopLocationUpdates()
            }
        })
        unitSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            ResourceHelper.setFahrenheit(isChecked, requireContext())
            var currentTemp : String = temperatureView.text.toString()
            if(isChecked){
                currentTemp = ResourceHelper.toFahrenheit(currentTemp)
            } else {
                currentTemp = ResourceHelper.toCelsius(currentTemp)
            }
            temperatureView.text = "$currentTemp ${ResourceHelper.getFahrenheitOrCelsius(requireContext())}"

        })

        //TODO: Refactor and probably not needed
        locationDataListener.locationPermissionStatusLiveData.observe(this.viewLifecycleOwner, Observer {
            if(it is PermissionStatus.Denied) {
                Log.d(TAG,"Show location permission dialog")
                if(askForLocationPermission(requireActivity())==1){
                    startLocationUpdates()
                }

            } else {

            }

            Log.d(TAG,"Permission status is $it")
        })





        pageViewModel.weatherImage.observe(this.viewLifecycleOwner, Observer<Drawable> {
            mainImageView.setImageDrawable(it)
        })
        pageViewModel.text.observe(this.viewLifecycleOwner, Observer<String> {
            statusTextView.text = it
        })
        pageViewModel.current_label.observe(this.viewLifecycleOwner, Observer<String> {
            currentTextView.text = it
        })
        pageViewModel.temperature.observe(this.viewLifecycleOwner, Observer<String> {
            temperatureView.text = "$it ${ResourceHelper.getFahrenheitOrCelsius(requireContext())}"
        })
      /*  pageViewModel.degreeLabel.observe(this.viewLifecycleOwner, Observer<Drawable> {
            degreeView.setImageDrawable(it)
        })

       */
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationEnabled = LocationHelper.checkLocationPermission(requireContext())
        updateLocationUI(locationEnabled)
        updateBackground()
        startLocationUpdates()
    }

    fun askForLocationPermission(activity: Activity) : Int {
        if (alertDialog?.isShowing == true) {
            return -1  // null or dialog already being shown
        }
        if (!LocationHelper.checkLocationPermission(activity)) {
            Log.d(TAG, "No permission given, showing dialog")
            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                alertDialog = AlertDialog.Builder(activity)
                    .setMessage(requireContext().getString(R.string.ask_use_permission))
                    .setPositiveButton(android.R.string.ok) { _, _ ->
                        requestPermissions(
                            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                            LocationHelper.PERMISSIONS_LOCATION
                        )
                    }
                    .setNegativeButton(android.R.string.cancel, null).create()
                alertDialog?.apply {
                    show()
                }
            } else {

                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LocationHelper.PERMISSIONS_LOCATION
                )

            }
            return 0 //wait
        } else {
            Log.d(TAG, "Permission given, not showing dialog")
            return 1
        }
    }



    private fun startLocationUpdates(){
        locationDataListener.getLocationData().removeObservers(this.viewLifecycleOwner)
        locationDataListener.getLocationData().observe(this.viewLifecycleOwner, Observer {
            Log.d(TAG, "Location data aquired: ${it.latitude}, ${it.longitude}")
            pageViewModel.updateLocation(it)
            currentWeatherViewModel.getCurrentWeather(requireContext(), it).observe(this.viewLifecycleOwner, Observer {
                Log.d(TAG, "Weather data aquired: $it")
                pageViewModel.setWeatherData(it)
                // latLong.text =  getString(R.string.latLong, it.longitude, it.latitude)

            })
            updateBackground()
        })
    }
    private fun stopLocationUpdates(){
        locationDataListener.getLocationData().removeObservers(this.viewLifecycleOwner)
    }
    private fun updateBackground(){

//This function will change background drawable, so place it where you want.
//This function will change background drawable, so place it where you want.
        var id : Int
        if(DateTimeHelper.isDayTime()) id = R.drawable.bg_day else id = R.drawable.bg_night
        constraintLayout.background = requireContext().getDrawable(id)
    }
    private fun updateLocationUI(on : Boolean){
        if(view != null) {
            var statusView = view!!.findViewById<TextView>(R.id.status_label)
            var text = ""//""Location data is allowed"
            if(!on){
                text = getString(R.string.no_location_check)
                val btnRetry = view!!.findViewById<ImageButton>(R.id.btn_retry)
                btnRetry.setVisibility(View.VISIBLE)
            } else {
                val btnRetry = view!!.findViewById<ImageButton>(R.id.btn_retry)
                btnRetry.setVisibility(View.INVISIBLE)
            }
            statusView.text = text
            val locationSwitch = view!!.findViewById<Switch>(R.id.location_switch)
            locationSwitch.isChecked = on
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        Log.d(TAG,"onRequestPermissionsResult")
        if(LocationHelper.arePermissionsResultYes(requestCode,
                permissions, grantResults, requireContext()
        )) {
            Log.d(TAG,"Location permission granted")
            startLocationUpdates()
            updateLocationUI(true)

        } else {
            Log.d(TAG,"Location permission not granted")
            stopLocationUpdates()
            updateLocationUI(false)
        }

    }


    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): CurrentWeatherFragment {
            return CurrentWeatherFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

}