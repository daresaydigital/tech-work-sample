package com.example.android.myhometownweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;
import java.util.ArrayList;

import sync.NetworkUtils;
import sync.WeatherAsyncTaskLoader;

public class MainFragment extends Fragment {
    ArrayList<Weather> weathers;
    private final int LOADER_VERSION = 1;
    private LoaderManager.LoaderCallbacks<ArrayList<Weather>> mLoaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<Weather>>() {
        @NonNull
        @Override
        public Loader<ArrayList<Weather>> onCreateLoader(int id, @Nullable Bundle args) {
            if (id == LOADER_VERSION){
                String currentLocation = MainActivity.locations.get(0);
                URL urlToQuery = NetworkUtils.buildUrl(currentLocation);
                return new WeatherAsyncTaskLoader(getContext(),urlToQuery);
            }
            return null;
        }

        @Override
        public void onLoadFinished(@NonNull Loader<ArrayList<Weather>> loader, ArrayList<Weather> data) {
            if (data != null){
                weathers = data;
                FragmentManager manager = getChildFragmentManager();
                FragmentTransaction wTransaction = manager.beginTransaction();
                FragmentTransaction fTransaction = manager.beginTransaction();
                WeatherFragment weatherFragment = WeatherFragment.newInstance(data);
                ForecastFragment forecastFragment = ForecastFragment.newInstance(data);
                wTransaction.replace(R.id.weather_fragment_container,weatherFragment).commit();
                fTransaction.replace(R.id.forecast_fragment_container,forecastFragment).commit();
            }
        }

        @Override
        public void onLoaderReset(@NonNull Loader<ArrayList<Weather>> loader) {
            weathers=null;
        }
    };
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(LOADER_VERSION,null,mLoaderCallbacks);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment,container,false);
        return rootView;
    }
}
