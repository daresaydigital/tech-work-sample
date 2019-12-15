package com.russellmorris.getlocation.ui.view

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.russellmorris.getlocation.BuildConfig
import com.russellmorris.getlocation.R
import com.russellmorris.getlocation.injectFeature
import com.russellmorris.location.LocationProvider
import com.russellmorris.location.LocationProviderImpl
import com.russellmorris.location.LocationResultListener
import kotlinx.android.synthetic.main.get_location_fragment.*

class GetLocationFragment : Fragment(), LocationResultListener {

    lateinit var locationProvider: LocationProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationProvider = LocationProviderImpl(requireContext(), this)
        locationProvider.initialiseLocationClient()
        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), BuildConfig.PLACES_API_KEY)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.get_location_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        locationPermissionButton.setOnClickListener{v -> activity?.let {
            locationProvider.getLocation(it)
        } }
    }

    override fun onResume() {
        super.onResume()
        setUpAutoComplete()
    }

    override fun locationPermissionPreviouslyDeniedWithNeverAskAgain() {
        val settingsIntent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val settingUri = Uri.fromParts("package", activity?.packageName, null)
        settingsIntent.setData(settingUri)
        startActivity(settingsIntent)
    }

    override fun setLocation(location: Location?) {
        // Launch weather details fragment
        println("Location: $location")
    }

    private fun setUpAutoComplete() {
        val autocompleteSupportFragment = childFragmentManager.findFragmentById(R.id.placeAutoComplete) as AutocompleteSupportFragment
        autocompleteSupportFragment.setPlaceFields(arrayListOf(Place.Field.ID, Place.Field.NAME))
        autocompleteSupportFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteSupportFragment.setHint(getString(R.string.seach_for_a_location))
        autocompleteSupportFragment.setOnPlaceSelectedListener(object:PlaceSelectionListener{
            override fun onPlaceSelected(p0: Place) {
                Toast.makeText(requireContext(), ""+p0.name, Toast.LENGTH_LONG).show()
            }

            override fun onError(p0: Status) {
                Toast.makeText(requireContext(), ""+p0.statusMessage, Toast.LENGTH_LONG).show()
            }

        })
    }

}
