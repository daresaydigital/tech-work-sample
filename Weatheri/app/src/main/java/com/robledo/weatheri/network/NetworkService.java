package com.robledo.weatheri.network;

import com.robledo.weatheri.models.CurrentWeather;
import com.robledo.weatheri.models.WeatherForecast;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {

    //Receive the city name and the api key
    @GET("weather")
    Observable<CurrentWeather> getCurrentWeather(@Query("q") String city, @Query("key") String key);

    @GET("forecast/daily")
    Observable<WeatherForecast> getDailyForecast(@Query("q") String city, @Query("key") String key);
}
