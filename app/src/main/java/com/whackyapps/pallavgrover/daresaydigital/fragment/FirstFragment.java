package com.whackyapps.pallavgrover.daresaydigital.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.whackyapps.pallavgrover.daresaydigital.MyApplication;
import com.whackyapps.pallavgrover.daresaydigital.R;
import com.whackyapps.pallavgrover.daresaydigital.activity.MainActivity;
import com.whackyapps.pallavgrover.daresaydigital.activity.WeatherForecastActivity;
import com.whackyapps.pallavgrover.daresaydigital.data.RemoteDataSource;
import com.whackyapps.pallavgrover.daresaydigital.data.model.Weather;
import com.whackyapps.pallavgrover.daresaydigital.data.retrofit.ApiClient;
import com.whackyapps.pallavgrover.daresaydigital.data.retrofit.ApiInterface;
import com.whackyapps.pallavgrover.daresaydigital.util.ConnectionDetector;
import com.whackyapps.pallavgrover.daresaydigital.util.Constants;
import com.whackyapps.pallavgrover.daresaydigital.util.PermissionUtil;
import com.whackyapps.pallavgrover.daresaydigital.util.Util;
import com.whackyapps.pallavgrover.daresaydigital.viewmodel.WeatherListViewModel;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.whackyapps.pallavgrover.daresaydigital.util.Util.saveLastUpdateTimeMillis;

public class FirstFragment extends android.support.v4.app.Fragment{

    public static final String TAG = "FirstFragment";
    private WeatherListViewModel mListViewModel;
    private SwipeRefreshLayout mRefreshLayout;
    private View root;
    private ProgressBar mLoadMorebar;
    private SearchView mSearchView;
    private Menu mToolbarMenu;

    private static final int REQUEST_LOCATION = 0;
    private static String[] PERMISSIONS_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private LocationManager locationManager;
    private View containerView;
    private ProgressDialog mProgressDialog;
    private ConnectionDetector connectionDetector;
    private Boolean isNetworkAvailable;
    private static final long LOCATION_TIMEOUT_IN_MS = 30000L;
    public static Weather mWeather;

    private TextView mIconWeatherView;
    private TextView mTemperatureView;
    private TextView mDescriptionView;
    private TextView mHumidityView;
    private TextView mWindSpeedView;
    private TextView mPressureView;
    private TextView mCloudinessView;
    private TextView mLastUpdateView;
    private TextView mSunriseView;
    private TextView mSunsetView;
    private AppBarLayout mAppBarLayout;
    private TextView mIconWindView;
    private TextView mIconHumidityView;
    private TextView mIconPressureView;
    private TextView mIconCloudinessView;
    private TextView mIconSunriseView;
    private TextView mIconSunsetView;

    private String mSpeedScale;
    private String mIconWind;
    private String mIconHumidity;
    private String mIconPressure;
    private String mIconCloudiness;
    private String mIconSunrise;
    private String mIconSunset;
    private String mPercentSign;
    private String mPressureMeasurement;
    private TextView cityName;
    private Context context;


    public static FirstFragment newInstance() {
        return new FirstFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_content, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        context = view.getContext();
        containerView = view;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        connectionDetector = new ConnectionDetector(context);
        weatherConditionsIcons();
        initializeTextView();
        mWeather = new Weather();
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.main_swipe_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mRefreshLayout.setRefreshing(true);
                mListViewModel.refreshWeatherData();
            }
        });
        mRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        subscribeUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.mToolbarMenu = menu;
        inflater.inflate(R.menu.activity_main_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_refresh:
                if (connectionDetector.isNetworkAvailableAndConnected()) {
                    mListViewModel.refreshWeatherData();
                    setUpdateButtonState(true);
                } else {
                    Toast.makeText(getContext(),
                            R.string.connection_not_found,
                            Toast.LENGTH_SHORT).show();
                    setUpdateButtonState(false);
                }
                return true;
            case R.id.main_menu_detect_location:
                requestLocation();
                return true;
            case R.id.main_menu_daily_forecast:
                Intent intent = new Intent(getActivity(), WeatherForecastActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void setUpdateButtonState(boolean isUpdate) {
        if (mToolbarMenu != null) {
            MenuItem updateItem = mToolbarMenu.findItem(R.id.main_menu_refresh);
            ProgressBar progressUpdate = (ProgressBar) containerView.findViewById(R.id.toolbar_progress_bar);
            if (isUpdate) {
                updateItem.setVisible(false);
                progressUpdate.setVisibility(View.VISIBLE);
            } else {
                progressUpdate.setVisibility(View.GONE);
                updateItem.setVisible(true);
            }
        }
    }

    private void requestLocation() {
        int fineLocationPermission = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        if (fineLocationPermission != PackageManager.PERMISSION_GRANTED) {
            requestLocationPermission();
        } else {
            detectLocation();
        }
    }
    private void requestLocationPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            Snackbar.make(containerView.findViewById(android.R.id.content), R.string.permission_location_rationale, Snackbar.LENGTH_LONG)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_LOCATION, REQUEST_LOCATION);
                        }
                    }).show();
        } else {
            ActivityCompat.requestPermissions(getActivity(), PERMISSIONS_LOCATION, REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION:
                if (PermissionUtil.verifyPermissions(grantResults)) {
                    Snackbar.make(containerView.findViewById(android.R.id.content), R.string.permission_available_location, Snackbar.LENGTH_SHORT).show();
                } else {
                    Snackbar.make(containerView.findViewById(android.R.id.content), R.string.permission_not_granted, Snackbar.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
                break;
        }
    }

    private void subscribeUI() {
        WeatherListViewModel.Factory factory = new WeatherListViewModel
                .Factory(MyApplication.getInstance()
                , RemoteDataSource.getInstance(),getContext());
        mListViewModel = ViewModelProviders.of(FirstFragment.this, factory).get(WeatherListViewModel.class);
        mListViewModel.getWeather().observe(this, new Observer<Weather>() {
            @Override
            public void onChanged(@Nullable Weather stories) {
                if (stories == null) {
                    return;
                }
                mWeather = stories;
                updateCurrentWeather();
                setUpdateButtonState(false);
            }
        });
        mListViewModel.getLoadMoreState().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean == null) {
                    return;
                }
                if (mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                    setUpdateButtonState(false);
                } else {
//                    mLoadMorebar.setVisibility(aBoolean ? View.VISIBLE : View.INVISIBLE);
                }
            }
        });
        mListViewModel.refreshWeatherData();
    }
    private void initializeTextView() {

        Typeface weatherFontIcon = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/weathericons-regular-webfont.ttf");

        mIconWeatherView = (TextView) containerView.findViewById(R.id.main_weather_icon);
        cityName = (TextView) containerView.findViewById(R.id.city_name);
        mTemperatureView = (TextView) containerView.findViewById(R.id.main_temperature);
        mDescriptionView = (TextView) containerView.findViewById(R.id.main_description);
        mPressureView = (TextView) containerView.findViewById(R.id.main_pressure);
        mHumidityView = (TextView) containerView.findViewById(R.id.main_humidity);
        mWindSpeedView = (TextView) containerView.findViewById(R.id.main_wind_speed);
        mCloudinessView = (TextView) containerView.findViewById(R.id.main_cloudiness);
        mLastUpdateView = (TextView) containerView.findViewById(R.id.main_last_update);
        mSunriseView = (TextView) containerView.findViewById(R.id.main_sunrise);
        mSunsetView = (TextView) containerView.findViewById(R.id.main_sunset);
        mAppBarLayout = (AppBarLayout) containerView.findViewById(R.id.main_app_bar);

        /**
         * Initialize and configure weather icons
         */
        mIconWeatherView.setTypeface(weatherFontIcon);
        mIconWindView = (TextView) containerView.findViewById(R.id.main_wind_icon);
        mIconWindView.setTypeface(weatherFontIcon);
        mIconWindView.setText(mIconWind);
        mIconHumidityView = (TextView) containerView.findViewById(R.id.main_humidity_icon);
        mIconHumidityView.setTypeface(weatherFontIcon);
        mIconHumidityView.setText(mIconHumidity);
        mIconPressureView = (TextView) containerView.findViewById(R.id.main_pressure_icon);
        mIconPressureView.setTypeface(weatherFontIcon);
        mIconPressureView.setText(mIconPressure);
        mIconCloudinessView = (TextView) containerView.findViewById(R.id.main_cloudiness_icon);
        mIconCloudinessView.setTypeface(weatherFontIcon);
        mIconCloudinessView.setText(mIconCloudiness);
        mIconSunriseView = (TextView) containerView.findViewById(R.id.main_sunrise_icon);
        mIconSunriseView.setTypeface(weatherFontIcon);
        mIconSunriseView.setText(mIconSunrise);
        mIconSunsetView = (TextView) containerView.findViewById(R.id.main_sunset_icon);
        mIconSunsetView.setTypeface(weatherFontIcon);
        mIconSunsetView.setText(mIconSunset);
    }

    private void weatherConditionsIcons() {
        mIconWind = getString(R.string.icon_wind);
        mIconHumidity = getString(R.string.icon_humidity);
        mIconPressure = getString(R.string.icon_barometer);
        mIconCloudiness = getString(R.string.icon_cloudiness);
        mPercentSign = getString(R.string.percent_sign);
        mPressureMeasurement = getString(R.string.pressure_measurement);
        mIconSunrise = getString(R.string.icon_sunrise);
        mIconSunset = getString(R.string.icon_sunset);
    }

    private void updateCurrentWeather() {

        mSpeedScale = getContext().getString(R.string.wind_speed_meters);
        String temperature = String.format(Locale.getDefault(), "%.0f",
                mWeather.getMain().getTemp());
        String pressure = String.format(Locale.getDefault(), "%.1f",
                mWeather.getMain().getPressure());
        String wind = String.format(Locale.getDefault(), "%.1f", mWeather.getWind().getSpeed());
        String lastUpdate = Util.setLastUpdateTime(getActivity(),
                saveLastUpdateTimeMillis(getContext()));
        String sunrise = Util.unixTimeToFormatTime(getContext(), mWeather.getSys().getSunrise());
        String sunset = Util.unixTimeToFormatTime(getContext(), mWeather.getSys().getSunset());

        cityName.setText(mWeather.getName());
        mIconWeatherView.setText(
                Util.getStrIcon(getContext(), mWeather.getWeather().get(0).getIcon()));
        mTemperatureView.setText(getString(R.string.temperature_with_degree, temperature));
            mDescriptionView.setText(mWeather.getWeather().get(0).getDescription());
        mHumidityView.setText(getString(R.string.humidity_label,
                String.valueOf(mWeather.getMain().getHumidity()),
                mPercentSign));
        mPressureView.setText(getString(R.string.pressure_label, pressure,
                mPressureMeasurement));
        mWindSpeedView.setText(getString(R.string.wind_label, wind, mSpeedScale));
        mCloudinessView.setText(getString(R.string.cloudiness_label,
                String.valueOf(mWeather.getCloud().getAll()),
                mPercentSign));
        mLastUpdateView.setText(getString(R.string.last_update_label, lastUpdate));
        mSunriseView.setText(getString(R.string.sunrise_label, sunrise));
        mSunsetView.setText(getString(R.string.sunset_label, sunset));

    }
    private void detectLocation() {
        boolean isGPSEnabled = locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)
                && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.getAllProviders().contains(LocationManager.NETWORK_PROVIDER)
                && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        mProgressDialog = new ProgressDialog(getContext());
        mProgressDialog.setMessage(getString(R.string.progressDialog_gps_locate));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(android.R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    locationManager.removeUpdates(mLocationListener);
                } catch (SecurityException e) {
                    Log.e(TAG, "Cancellation error", e);
                }
            }
        });

        if (isNetworkEnabled) {
            networkRequestLocation();
            mProgressDialog.show();
        } else {
            if (isGPSEnabled) {
                gpsRequestLocation();
                mProgressDialog.show();
            } else {
                showSettingsAlert();
            }
        }
    }

    public void showSettingsAlert() {
        AlertDialog.Builder settingsAlert = new AlertDialog.Builder(getContext());
        settingsAlert.setTitle(R.string.alertDialog_gps_title);
        settingsAlert.setMessage(R.string.alertDialog_gps_message);

        settingsAlert.setPositiveButton(R.string.alertDialog_gps_positiveButton,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent goToSettings = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(goToSettings);
                    }
                });

        settingsAlert.setNegativeButton(R.string.alertDialog_gps_negativeButton,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        settingsAlert.show();
    }

    public void gpsRequestLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Looper locationLooper = Looper.myLooper();
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, mLocationListener, locationLooper);
            final Handler locationHandler = new Handler(locationLooper);
            locationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    locationManager.removeUpdates(mLocationListener);
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location lastLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (lastLocation != null) {
                            mLocationListener.onLocationChanged(lastLocation);
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
                        }
                    }
                }
            }, LOCATION_TIMEOUT_IN_MS);
        }
    }

    public void networkRequestLocation() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Looper locationLooper = Looper.myLooper();
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, mLocationListener, locationLooper);
            final Handler locationHandler = new Handler(locationLooper);
            locationHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    locationManager.removeUpdates(mLocationListener);
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location lastNetworkLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        Location lastGpsLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                        if ((lastGpsLocation == null) && (lastNetworkLocation != null)) {
                            mLocationListener.onLocationChanged(lastNetworkLocation);
                        } else if ((lastGpsLocation != null) && (lastNetworkLocation == null)) {
                            mLocationListener.onLocationChanged(lastGpsLocation);
                        } else {
                            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                        }
                    }
                }
            }, LOCATION_TIMEOUT_IN_MS);
        }
    }
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mProgressDialog.cancel();
            String latitude = String.format("%1$.2f", location.getLatitude());
            String longitude = String.format("%1$.2f", location.getLongitude());

            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                locationManager.removeUpdates(mLocationListener);
            }

            connectionDetector = new ConnectionDetector(getContext());
            isNetworkAvailable = connectionDetector.isNetworkAvailableAndConnected();

            if (isNetworkAvailable) {
                ApiInterface apiService =
                        ApiClient.getClient().create(ApiInterface.class);
                apiService.getLongLatWeather(longitude,latitude, Constants.OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<Weather>() {
                    @Override
                    public void onResponse(Call<Weather> call, Response<Weather> response) {
                        if (response.isSuccessful()) {
                            mWeather = response.body();
                            updateCurrentWeather();
                        }
                    }

                    @Override
                    public void onFailure(Call<Weather> call, Throwable t) {
                    }
                });
            } else {
                Toast.makeText(getContext(), R.string.connection_not_found, Toast.LENGTH_SHORT)
                        .show();
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
    };

}
