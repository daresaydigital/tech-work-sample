package com.whackyapps.pallavgrover.daresaydigital.data.retrofit;

import com.whackyapps.pallavgrover.daresaydigital.data.model.Weather;
import com.whackyapps.pallavgrover.daresaydigital.data.model.WeatherForecast;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface ApiInterface {
    @GET("/weather")
    Call<Weather> getWeatherList(@Query("q") String city,@Query("key") String apiKey);

    @GET("/weather")
    Call<Weather> getLongLatWeather(@Query("lon") String lon,@Query("lat") String lat,@Query("key") String apiKey);

    @GET("/forecast/daily")
    Call<WeatherForecast> getForecastDaily(@Query("q") String city, @Query("key") String apiKey);

}
