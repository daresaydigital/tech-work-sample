package com.robledo.weatheri.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.robledo.weatheri.R;
import com.robledo.weatheri.adapters.DailyForecastAdapter;
import com.robledo.weatheri.interfaces.MainViewContract;
import com.robledo.weatheri.models.List;
import com.robledo.weatheri.network.NetworkModule;
import com.robledo.weatheri.network.NetworkService;
import com.robledo.weatheri.presenters.MainPresenter;
import com.robledo.weatheri.utils.AnimationUtils;
import com.robledo.weatheri.utils.ViewUtils;

import java.io.IOException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MainViewContract.View, LocationListener {

    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 1;
    private boolean locationPermissionGranted = false;
    private boolean locationUpdatesSet = false;
    private TextView iconTV;
    private TextView tempTV;
    private TextView descriptionTV;
    private TextView cityTV;
    private RecyclerView daysRV;
    private ProgressBar waitPB;

    private MainPresenter presenter;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.setStatusBarTranslucent(this, true);
        setContentView(R.layout.activity_main);

        initializeViews();
        initViews();

        checkPermissions();

        NetworkService networkService = NetworkModule.createService(NetworkService.class);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        presenter = new MainPresenter(networkService, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationPermissionGranted)
            permissionIsGranted();
        else
            permissionIsDenied();
    }

    /**
     * Get location of the user and give it to the presenter, a flag handles if the request for updates
     * is already set
     */
    private void permissionIsGranted() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && !locationUpdatesSet) {
            descriptionTV.setText(getString(R.string.main_message_getting_location));
            locationUpdatesSet = true;
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    /**
     * Set the views to show that the permission is not granted
     */
    private void permissionIsDenied() {
        descriptionTV.setText(getString(R.string.main_warning_permission));
        removeWait();
    }

    private void initializeViews() {
        iconTV = findViewById(R.id.icon_textview);
        tempTV = findViewById(R.id.temp_textview);
        descriptionTV = findViewById(R.id.desc_weather_textview);
        cityTV = findViewById(R.id.city_textview);
        daysRV = findViewById(R.id.days_recyclerview);
        waitPB = findViewById(R.id.wait_progressbar);
    }

    private void initViews() {
        iconTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationUtils.animateExpandCollapse(iconTV);
            }
        });
    }

    /**
     * Check FINE LOCATION permission, request it or set the location flag to true
     */
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_FINE_LOCATION);
        else
            locationPermissionGranted = true;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    /////// Methods of the contract with the presenter
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void showWait() {
        waitPB.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeWait() {
        waitPB.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onFailure(String appErrorMessage) {
        descriptionTV.setText(getString(R.string.error_message));
    }

    @Override
    public void showCurrentWeather(Double temperature, String description, String idIcon) {
        tempTV.setText(String.format(Locale.US,"%.0f°", temperature));
        AnimationUtils.animateScaleShow(iconTV);
        AnimationUtils.animateAlphaShow(tempTV, 2000);
        descriptionTV.setText(description);
        iconTV.setText(idIcon);
    }

    @Override
    public void showWeatherForecast(java.util.List<List> dailyWeatherList) {
        if(dailyWeatherList != null && !dailyWeatherList.isEmpty()) {
            DailyForecastAdapter forecastAdapter = new DailyForecastAdapter(this, dailyWeatherList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            daysRV.setLayoutAnimation(android.view.animation.AnimationUtils.loadLayoutAnimation(this, R.anim.layout_animation_from_right));
            daysRV.setItemAnimator(new DefaultItemAnimator());
            daysRV.setLayoutManager(layoutManager);
            daysRV.setAdapter(forecastAdapter);
        }
    }

    @Override
    public void showCityName(String cityName) {
        AnimationUtils.animateAlphaShow(cityTV, 2000);
        cityTV.setText(cityName);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Called before onResume when requesting a permission, set a flag according to users answer
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                locationPermissionGranted = grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////
    //////// Methods to update location
    ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get city from location with geocoder class and give it to the presenter to request data
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        Geocoder geoCoder = new Geocoder(this, Locale.ENGLISH);
        java.util.List<Address> list = null;
        try {
            list = geoCoder.getFromLocation(location
                    .getLatitude(), location.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (list != null & list.size() > 0) {
            presenter.viewIsReady(list.get(0).getLocality(), list.get(0).getCountryCode());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
    ///////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onDestroy() {
        super.onDestroy();

        presenter.onStop();
    }
}
