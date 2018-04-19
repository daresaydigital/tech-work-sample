package com.babak.weather.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.SearchRecentSuggestions;
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

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_activity_toolbar) Toolbar toolbar;
    @BindView(R.id.main_activity_toolbar_search) SearchView searchView;
    private WeatherClient weatherClient;
    private SharedPreferences sharedPref;
    private boolean shouldRefresh = false;
    private Coord currentLocation = new Coord();

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
        sharedPref = getPreferences(Context.MODE_PRIVATE);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        handleIntent(getIntent());
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
                SharedPreferences.Editor editor = sharedPref.edit();
                boolean isCelsius = sharedPref.getBoolean(getString(R.string.sp_scale_key), true);
                isCelsius = !isCelsius;
                editor.putBoolean(getString(R.string.sp_scale_key), isCelsius);
                editor.apply();

                WeatherFragment wf = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment_container);
                if(wf != null) {
                    wf.setupTemps();
                }

                setToolbarScaleText(item, isCelsius);
                break;
            case R.id.main_toolbar_search_with_gps:
                // TODO
                // To be implemented.
                break;
            case R.id.main_toolbar_refresh:
                shouldRefresh = true;
                WeatherClient.getClient()
                        .getWeatherByPosition(currentLocation.getLat(),currentLocation.getLon())
                        .enqueue(new NetworkCallback());
                break;
        }
        return super.onOptionsItemSelected(item);
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

    private class NetworkCallback implements retrofit2.Callback<WeatherResponse> {

        @Override
        public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
            if (response.isSuccessful()) {
                int statusCode = response.body().getCod();

                if (statusCode == 200) {
                    currentLocation.setLat(response.body().getCoord().getLat());
                    currentLocation.setLon(response.body().getCoord().getLon());

                    if(shouldRefresh) {
                        WeatherFragment wf = (WeatherFragment) getSupportFragmentManager().findFragmentById(R.id.main_activity_fragment_container);
                        if(wf != null) {
                            shouldRefresh = false;
                            wf.refreshValues(response.body());
                        }
                        return;
                    }

                    MenuItem refresh = (MenuItem) toolbar.getMenu().findItem(R.id.main_toolbar_refresh);
                    if(!refresh.isVisible()) {
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
