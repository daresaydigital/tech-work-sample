package com.russellmorris.getlocation.ui.view

import android.content.Intent
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.russellmorris.getlocation.BuildConfig
import com.russellmorris.getlocation.R
import com.russellmorris.getlocation.injectFeature
import com.russellmorris.getlocation.ui.viewmodel.GetLocationViewModel
import com.russellmorris.location.LocationProvider
import com.russellmorris.location.LocationProviderImpl
import com.russellmorris.location.LocationResultListener
import com.russellmorris.presentation.base.BaseFragment
import com.russellmorris.presentation.base.BaseViewModel
import kotlinx.android.synthetic.main.get_location_fragment.*
import org.koin.androidx.viewmodel.ext.viewModel

class GetLocationFragment : BaseFragment(), LocationResultListener {

    private val getLocationViewModel: GetLocationViewModel by viewModel()
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

    override fun getViewModel(): BaseViewModel = getLocationViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injectFeature()
        locationPermissionButton.setOnClickListener{_ -> activity?.let {
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

    override fun setLocation(location: Location) {
        launchNextView(location.latitude, location.longitude)
    }

    private fun setUpAutoComplete() {
        val autocompleteSupportFragment = childFragmentManager.findFragmentById(R.id.placeAutoComplete) as AutocompleteSupportFragment
        autocompleteSupportFragment.setPlaceFields(arrayListOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))
        autocompleteSupportFragment.setTypeFilter(TypeFilter.CITIES)
        autocompleteSupportFragment.setHint(getString(R.string.seach_for_a_location))
        autocompleteSupportFragment.setOnPlaceSelectedListener(object:PlaceSelectionListener{
            override fun onPlaceSelected(place: Place) {
                launchNextView(place.latLng?.latitude, place.latLng?.longitude)
            }

            override fun onError(status: Status) {
                getLocationViewModel.snackBarError
            }

        })
    }

    private fun launchNextView(latitude: Double?, longitude: Double?) {
        if (findNavController().currentDestination?.id == R.id.locationFragment) {
            getLocationViewModel.navigate(
                GetLocationFragmentDirections.actionLaunchesFragmentToDetailFragment(
                    latitude.toString(), longitude.toString()
                )
            )
        }
    }

}
