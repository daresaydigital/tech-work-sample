package com.example.nejat.weatherapp.Adapter.ForcastAdapter;

import com.example.nejat.weatherapp.API.IRetrofitClientInstance;
import com.example.nejat.weatherapp.API.RetrofitClientInstance;
import com.example.nejat.weatherapp.POJO.Forecast;
import com.example.nejat.weatherapp.Utils.Common;

import retrofit2.Call;

public class ForcastAdapterModel implements ForcastAdapterContract.ForecastAdapterModel {
    @Override
    public void fetchWeatherAPI() {
        Common common = new Common();
        IRetrofitClientInstance service = RetrofitClientInstance.getRetrofitInstance().create(IRetrofitClientInstance.class);
        Call<Forecast> call = service.getForecastData(common.getLat(), common.getLon());

    }
}
