package com.babak.weather.networking;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.babak.weather.models.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class WeatherClient {
    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://worksample-api.herokuapp.com/";
    private static final String API_KEY = "";
    private static WeatherService weatherService = null;

    private WeatherClient() {
    }

    public static WeatherService getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            weatherService = retrofit.create(WeatherClient.WeatherService.class);
        }
        return weatherService;
    }

    public interface WeatherService {
        @GET("weather?key=" + API_KEY)
        Call<WeatherResponse> getWeatherByCity(@Query("q") String cityName);

        @GET("weather?key=" + API_KEY)
        Call<WeatherResponse> getWeatherByPosition(@Query("lat") Double latitude, @Query("lon") Double longitude);
    }
}
