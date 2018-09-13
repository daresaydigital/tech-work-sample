package com.jonassjoberg.daresay_awesome_weather_app;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private Button mButtonSearch;
    private CustomWeatherTextView mTextViewCity;
    private CustomWeatherTextView mTextViewTemp;
    private CustomWeatherTextView mTextViewIcon;
    private CustomWeatherTextView mTextViewSunrise;
    private CustomWeatherTextView mTextViewSunset;
    private CustomWeatherTextView mTextViewWindRotation;
    private CustomWeatherTextView mTextViewWind;
    private ProgressBar mProgressBar;
    private String cityString;
    private String tempString;
    private String iconString;
    private String sunriseText;
    private String sunsetText;
    private String windText;
    private float windRotation = 0.f;
    private EditText mEditText;
    private HerokuappAPI mHerokuAPI;

    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    private double latitude;
    private double longitude;

    private boolean HAS_PERMISSION = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonSearch = findViewById(R.id.mainActivityButtonSearch);
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mHerokuAPI.getWeatherByCityName(mEditText.getText().toString());
            }
        });
        mEditText = findViewById(R.id.mainActivityEditTextSearch);
        mTextViewCity = findViewById(R.id.mainActivityTextViewCity);
        mTextViewTemp = findViewById(R.id.mainActivityTextViewTemp);
        mTextViewIcon = findViewById(R.id.mainActivityTextViewIcon);
        mTextViewSunrise = findViewById(R.id.mainActivityTextViewSunriseTime);
        mTextViewSunset = findViewById(R.id.mainActivityTextViewSunsetTime);
        mTextViewWind = findViewById(R.id.mainActivityTextViewWindText);
        mTextViewWindRotation = findViewById(R.id.mainActivityTextViewWind);
        mProgressBar = findViewById(R.id.mainActivityProgressBar);

        mHerokuAPI = new HerokuappAPI();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        updateLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.refresh);
        String title = menuItem.getTitle().toString();

        Button buttonMenu = (Button) menuItem.getActionView();
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/weathericons-regular-webfont.ttf");
        buttonMenu.setTypeface(typeface);
        buttonMenu.setText(title);
        buttonMenu.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
        buttonMenu.setTextColor(Color.WHITE);
        buttonMenu.setBackgroundColor(getResources().getColor(R.color.colorTransparent));
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(menuItem);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                checkLocation();
                updateLocation();
                mHerokuAPI.getWeatherByGeographicCoordinates(latitude, longitude);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean checkLocation() {
        if (!isLocationEnabled()) {
            showAlert();
        }
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
            .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to use this app")
            .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                }
            });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void updateLocation() {
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(Constants.UPDATE_INTERVAL)
                .setFastestInterval(Constants.FASTEST_INTERVAL);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (mGoogleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                }
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (mGoogleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                }
                mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
        }

        if (mLocation != null) {
            latitude = mLocation.getLatitude();
            longitude = mLocation.getLongitude();
        } else {
            Toast.makeText(this, "No location detected yet", Toast.LENGTH_SHORT).show();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    private class HerokuappAPI {

        private final String TAG = HerokuappAPI.class.toString();

        public HerokuappAPI() {

        }

        public void getWeatherByCityName(String cityName) {
            String urlString = Constants.BASE_URL + "?q=" + cityName + "&key=" + Constants.API_KEY;System.out.println(urlString);
            PostFetcher postFetcher = new PostFetcher();
            postFetcher.execute(urlString);
        }

        public void getWeatherByCityName(String cityName, String countryCode) {
            String urlString = Constants.BASE_URL + "?q=" + cityName + "," + countryCode + "&key=" + Constants.API_KEY;
            PostFetcher postFetcher = new PostFetcher();
            postFetcher.execute(urlString);
        }

        public void getWeatherByID(String cityID) {
            String urlString = Constants.BASE_URL + "?id=" + cityID + "&key=" + Constants.API_KEY;
            System.out.println(urlString);
            PostFetcher postFetcher = new PostFetcher();
            postFetcher.execute(urlString);
        }

        public void getWeatherByGeographicCoordinates(double lat, double lon) {
            String urlString = Constants.BASE_URL + "?lat=" + lat + "&lon=" + lon + "&key=" + Constants.API_KEY;
            System.out.println(urlString);
            PostFetcher postFetcher = new PostFetcher();
            postFetcher.execute(urlString);
        }

        public void getWeatherByZIPCode(int zipCode, String countryCode) {
            String urlString = Constants.BASE_URL + "?zip=" + zipCode + "," + countryCode + "&key=" + Constants.API_KEY;
            PostFetcher postFetcher = new PostFetcher();
            postFetcher.execute(urlString);
        }

        private class PostFetcher extends AsyncTask<String, Void, String> {
            private String urlString = null;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected String doInBackground(String... urlStrings) {
                if (urlStrings != null) {
                    if (urlStrings.length > 0) {
                        urlString = urlStrings[0];
                    }
                }

                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet httpGet = new HttpGet(urlString);
                    HttpResponse response = client.execute(httpGet);
                    StatusLine statusLine = response.getStatusLine();

                    if (statusLine.getStatusCode() == 200) {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();

                        try {
                            String jsonString = IOUtils.toString(content, "UTF-8");
                            JSONObject jsonObject = new JSONObject(jsonString);
                            int cod = jsonObject.getInt("cod");

                            if (cod == 200) {
                                GsonBuilder gsonBuilder = new GsonBuilder();
                                Gson gson = gsonBuilder.create();
                                Post post = gson.fromJson(jsonString, Post.class);
                                handlePost(post);
                            } else {
                                String message = cod + ":" + jsonObject.getInt("message");
                                Log.e(TAG, "Failed to retrieve JSON from API due to: " + message);
                                failedLoadingPost(message);
                            }
                            content.close();
                        } catch (Exception e) {
                            Log.e(TAG, "Failed to parse JSON due to: " + e);
                            failedLoadingPost("Could not read weather data");
                        }
                    } else {
                        Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
                        failedLoadingPost("Retry connection");
                    }
                } catch(Exception e) {
                    Log.e(TAG, "Failed to send HTTP GET request due to: " + e);
                    failedLoadingPost("Retry connection");
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                mProgressBar.setVisibility(View.INVISIBLE);
                if (cityString != null && cityString != "") {
                    mTextViewCity.setText(cityString);
                } else {
                    mTextViewCity.setText(R.string.city_text);
                }
                if (tempString != null && tempString != "") {
                    mTextViewTemp.setText(tempString);
                } else {
                    mTextViewTemp.setText(R.string.wi_celsius_starting_value);
                }
                if (iconString != null && iconString != "") {
                    mTextViewIcon.setText(getStringResourceByName(iconString));
                } else {
                    mTextViewIcon.setText(R.string.wi_owm_800);
                }
                if (sunriseText != null && sunriseText != "") {
                    mTextViewSunrise.setText(sunriseText);
                } else {
                    mTextViewSunrise.setText(R.string.clock_text_neutral);
                }
                if (sunsetText != null && sunsetText != "") {
                    mTextViewSunset.setText(sunsetText);
                } else {
                    mTextViewSunset.setText(R.string.clock_text_neutral);
                }
                if (windText != null && windText != "") {
                    mTextViewWind.setText(windText);
                    mTextViewWindRotation.setRotation(windRotation);
                } else {
                    mTextViewWind.setText(R.string.wind_text);
                    mTextViewWindRotation.setRotation(0);
                }
            }
        }

        private void handlePost(Post post) {
            // TODO
            cityString = post.name;
            tempString = post.main.temp + getResources().getString(R.string.wi_celsius);
            if (post.weather.size() > 0) {
                iconString = "wi_owm_" + post.weather.get(0).id;
            }
            java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
            Date dateSunrise = new Date();
            long tmpSunrise = post.sys.sunrise;
            tmpSunrise *=1000;
            dateSunrise.setTime(tmpSunrise);
            sunriseText = simpleDateFormat.format(dateSunrise);
            Date dateSunset = new Date();
            long tmpSunset = post.sys.sunset;
            tmpSunset *=1000;
            dateSunset.setTime(tmpSunset);
            sunsetText = simpleDateFormat.format(dateSunset);
            windText = post.wind.speed + " m/s";
            windRotation = post.wind.deg;

            System.out.println(post.toString());
        }

        private void failedLoadingPost(final String message) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }

        private String getStringResourceByName(String string) {
            return getString(getResources().getIdentifier(string, "string", getPackageName()));
        }
    }
}
