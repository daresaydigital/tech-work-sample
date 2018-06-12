package com.whackyapps.pallavgrover.daresaydigital.activity;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.whackyapps.pallavgrover.daresaydigital.R;
import com.whackyapps.pallavgrover.daresaydigital.adapter.WeatherForecastAdapter;
import com.whackyapps.pallavgrover.daresaydigital.data.model.Weather;
import com.whackyapps.pallavgrover.daresaydigital.data.model.WeatherForecast;
import com.whackyapps.pallavgrover.daresaydigital.data.retrofit.ApiClient;
import com.whackyapps.pallavgrover.daresaydigital.data.retrofit.ApiInterface;
import com.whackyapps.pallavgrover.daresaydigital.util.ConnectionDetector;
import com.whackyapps.pallavgrover.daresaydigital.util.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WeatherForecastActivity extends AppCompatActivity {

    private final String TAG = "WeatherForecastActivity";

    private List<WeatherForecast.WeatherForecastOneDay> mWeatherForecastList;
    private ConnectionDetector mConnectionDetector;
    private RecyclerView mRecyclerView;
    private static Handler mHandler;
    private ProgressDialog mGetWeatherProgress;
    private WeatherForecast mWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mConnectionDetector = new ConnectionDetector(this);
        mWeatherForecastList = new ArrayList<>();
        mGetWeatherProgress = getProgressDialog();

        mRecyclerView = (RecyclerView) findViewById(R.id.forecast_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void updateUI() {
        ImageView android = (ImageView) findViewById(R.id.android);
        if (mWeatherForecastList.size() < 5) {
            mRecyclerView.setVisibility(View.INVISIBLE);
            android.setVisibility(View.VISIBLE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
            android.setVisibility(View.GONE);
        }
        WeatherForecastAdapter adapter = new WeatherForecastAdapter(this,
                mWeatherForecastList,
                getSupportFragmentManager());
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        setVisibleUpdating(true);
        getWeather();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.weather_forecast_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_forecast_refresh:
                if (mConnectionDetector.isNetworkAvailableAndConnected()) {
                    mWeatherForecastList.clear();
                    getWeather();
                    setVisibleUpdating(true);
                } else {
                    Toast.makeText(WeatherForecastActivity.this,
                            R.string.connection_not_found,
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getWeather() {
        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);
        apiService.getForecastDaily(Constants.CITY_NAME, Constants.OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<WeatherForecast>() {
            @Override
            public void onResponse(Call<WeatherForecast> call, Response<WeatherForecast> response) {
                if (response.isSuccessful()) {
                    mWeather = response.body();
                    mWeatherForecastList.addAll(mWeather.getWeatherForecastOneDay());
                    updateUI();
                    setVisibleUpdating(false);
                }
            }

            @Override
            public void onFailure(Call<WeatherForecast> call, Throwable t) {
                Log.d(TAG, "onFailure: "+t.getMessage());
                setVisibleUpdating(false);
            }
        });
    }

    private void setVisibleUpdating(boolean visible) {
        if (visible) {
            mGetWeatherProgress.show();
        } else {
            mGetWeatherProgress.cancel();
        }
    }

    private ProgressDialog getProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.isIndeterminate();
        dialog.setMessage(getString(R.string.load_progress));
        dialog.setCancelable(false);
        return dialog;
    }
}
