package com.example.nejat.weatherapp.API;

import com.example.nejat.weatherapp.POJO.Forecast;
import com.example.nejat.weatherapp.POJO.WeatherResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IRetrofitClientInstance {

    @GET(RetrofitClientInstance.WEATHER_URL)
    Call<WeatherResponse> getWeatherData(@Query("lat") double lat, @Query("lon") double longitude);

    @GET(RetrofitClientInstance.FORECAST_URL)
    Call<Forecast> getForecastData(@Query("lat") double lat, @Query("lon") double longitude);
}
