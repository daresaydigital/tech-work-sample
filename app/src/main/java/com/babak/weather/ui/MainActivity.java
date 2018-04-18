package com.babak.weather.ui;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.babak.weather.R;
import com.babak.weather.models.WeatherResponse;
import com.babak.weather.networking.WeatherClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.main_activity_toolbar) Toolbar toolbar;
    @BindView(R.id.main_activity_toolbar_search) SearchView searchView;
    private WeatherClient weatherClient;

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

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.clear_recent_searches:
                SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                        SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
                suggestions.clearHistory();
                break;
            case R.id.switch_scale:
                // TODO
                // To be implemented.
                break;
            case R.id.search_with_gps:
                // TODO
                // To be implemented.
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                    SuggestionProvider.AUTHORITY, SuggestionProvider.MODE);
            suggestions.saveRecentQuery(query, null);
            // TODO
            // Handle query. Save only queries in recent seuggestions that return a result?
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
                    WeatherFragment weatherFragment = WeatherFragment.newInstance(response.body());

                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.main_activity_fragment_container, weatherFragment).commit();
                } else if (statusCode == 404) {
                    Toast.makeText(MainActivity.this,
                            R.string.network_error_city_not_found, Toast.LENGTH_SHORT).show();
                }
            }

        }

        @Override
        public void onFailure(Call<WeatherResponse> call, Throwable t) {
            Toast.makeText(MainActivity.this,
                    R.string.network_error_no_connection, Toast.LENGTH_SHORT).show();
        }
    }
}
