package com.example.nejat.weatherapp.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClientInstance {
    private static Retrofit retrofit = null;
    public static final String API_KEY = "62fc4256-8f8c-11e5-8994-feff819cdc9f";
    public static final String BASE_URL = "https://worksample-api.herokuapp.com";
    public static final String WEATHER_URL = "/weather?&key=" + API_KEY;
    public static final String FORECAST_URL = "/forecast?&key=" +API_KEY;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}

