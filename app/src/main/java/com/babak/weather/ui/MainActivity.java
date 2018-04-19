package com.babak.weather.ui;

import android.Manifest;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.babak.weather.R;
import com.babak.weather.models.Coord;
import com.babak.weather.models.WeatherResponse;
import com.babak.weather.networking.WeatherClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements LocationListener {
    @BindView(R.id.main_activity_toolbar) Toolbar toolbar;
    @BindView(R.id.main_activity_toolbar_search) SearchView searchView;
    private static final int ACCESS_FINE_LOCATION_REQUEST = 1234;

    private WeatherClient weatherClient;
    private SharedPreferences sharedPref;
    private boolean shouldRefresh = false;

    private Coord currentLocation = new Coord();

    private LocationManager locationManager;
    private Criteria criteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.onBackPressed();
            }
        });

        setupLocation();
        sharedPref = getPreferences(Context.MODE_PRIVATE);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        handleIntent(getIntent());
    }

    private void setupLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        criteria.setCostAllowed(true);
        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        handleIntent(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_toolbar, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);

        boolean isCelsius = sharedPref.getBoolean(getString(R.string.sp_scale_key), true);

        MenuItem scaleItem = menu.findItem(R.id.main_toolbar_switch_scale);

        setToolbarScaleText(scaleItem, isCelsius);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.main_toolbar_clear_recent_searches:
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                        SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
                suggestions.clearHistory();
                break;
            case R.id.main_toolbar_switch_scale:
                switchScale(item);
                break;
            case R.id.main_toolbar_search_with_gps:
                searchWithGps();
                break;
            case R.id.main_toolbar_refresh:
                shouldRefresh = true;
                WeatherClient.getClient()
                        .getWeatherByPosition(currentLocation.getLat(), currentLocation.getLon())
                        .enqueue(new NetworkCallback());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchScale(MenuItem item) {
        SharedPreferences.Editor editor = sharedPref.edit();
        boolean isCelsius = sharedPref.getBoolean(getString(R.string.sp_scale_key), true);
        isCelsius = !isCelsius;
        editor.putBoolean(getString(R.string.sp_scale_key), isCelsius);
        editor.apply();

        WeatherFragment wf = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment_container);
        if (wf != null) {
            wf.setupTemps();
        }

        setToolbarScaleText(item, isCelsius);
    }

    private void searchWithGps() {
        if (!locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Toast.makeText(this, R.string.gps_error_not_enabled, Toast.LENGTH_SHORT).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_REQUEST);
        } else {
            locationManager.requestSingleUpdate(criteria, this, null);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == ACCESS_FINE_LOCATION_REQUEST &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestSingleUpdate(criteria, this, null);
        }
    }


    private void setToolbarScaleText(MenuItem scaleItem, boolean isCelsius) {
        if (isCelsius) {
            scaleItem.setTitle(R.string.toolbar_switch_scale_celsius);
        } else {
            scaleItem.setTitle(R.string.toolbar_switch_scale_fahrenheit);
        }
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            searchView.setQuery("", false);
            searchView.clearFocus();
            WeatherClient.getClient().getWeatherByCity(query).enqueue(new NetworkCallback());
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        WeatherClient.getClient().getWeatherByPosition(location.getLatitude(), location.getLongitude())
                .enqueue(new NetworkCallback());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private class NetworkCallback implements retrofit2.Callback<WeatherResponse> {

        @Override
        public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
            if (response.isSuccessful()) {
                int statusCode = response.body().getCod();

                if (statusCode == 200) {
                    currentLocation.setLat(response.body().getCoord().getLat());
                    currentLocation.setLon(response.body().getCoord().getLon());

                    if (shouldRefresh) {
                        WeatherFragment wf = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment_container);
                        if (wf != null) {
                            shouldRefresh = false;
                            wf.refreshValues(response.body());
                        }
                        return;
                    }

                    MenuItem refresh = (MenuItem) toolbar.getMenu().findItem(R.id.main_toolbar_refresh);
                    if (!refresh.isVisible()) {
                        refresh.setVisible(true);
                    }

                    WeatherFragment weatherFragment = WeatherFragment.newInstance(response.body());
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_fragment_container, weatherFragment).commit();
                } else if (statusCode == 404) {
                    Toast.makeText(MainActivity.this,
                            R.string.network_error_city_not_found, Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onFailure(Call<WeatherResponse> call, Throwable t) {
            shouldRefresh = false;
            Toast.makeText(MainActivity.this,
                    R.string.network_error_no_connection, Toast.LENGTH_SHORT).show();
        }
    }
}
