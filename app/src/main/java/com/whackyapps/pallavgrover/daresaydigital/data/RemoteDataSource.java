package com.whackyapps.pallavgrover.daresaydigital.data;


import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.whackyapps.pallavgrover.daresaydigital.data.model.Weather;
import com.whackyapps.pallavgrover.daresaydigital.data.retrofit.ApiClient;
import com.whackyapps.pallavgrover.daresaydigital.data.retrofit.ApiInterface;
import com.whackyapps.pallavgrover.daresaydigital.util.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteDataSource implements DataSource {

    private static RemoteDataSource INSTANCE = null;

    private final MutableLiveData<Boolean> mIsLoadingFood;


    private final MutableLiveData<Weather> mWeather;

    private ApiInterface apiService;



    {
        mIsLoadingFood = new MutableLiveData<>();
        mWeather = new MutableLiveData<>();

    }

    private RemoteDataSource() {
        apiService =
                ApiClient.getClient().create(ApiInterface.class);
    }

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (RemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new RemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }


    @Override
    public LiveData<Weather> getWeatherList(String city) {
        mIsLoadingFood.setValue(true);
        apiService.getWeatherList(city, Constants.OPEN_WEATHER_MAP_API_KEY).enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                mIsLoadingFood.setValue(false);
                if (response.isSuccessful()) {
                    mWeather.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                mIsLoadingFood.setValue(false);

            }
        });
        return mWeather;
    }

    @Override
    public MutableLiveData<Boolean> isLoadingWeather() {
        return mIsLoadingFood;
    }
}
