package com.example.android.myhometownweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ForecastFragment extends Fragment{
    ArrayList<Weather> mWeathers;
    public static ForecastFragment newInstance(ArrayList<Weather> weathers){
        ForecastFragment forecastFragment = new ForecastFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("weathers",weathers);
        forecastFragment.setArguments(args);
        return forecastFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeathers = getArguments()!=null? getArguments().<Weather>getParcelableArrayList("weathers"):null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View forecast = inflater.inflate(R.layout.forecast_fragment,container,false);
        RecyclerView recyclerView = forecast.findViewById(R.id.forecast_recylcer_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);
        ForecastFragmentAdapter adapter = new ForecastFragmentAdapter(getContext());
        adapter.setmForecasts(mWeathers);
        recyclerView.setAdapter(adapter);
        return forecast;
    }
}
