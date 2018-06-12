package com.whackyapps.pallavgrover.daresaydigital.data;

import android.arch.lifecycle.LiveData;

import com.whackyapps.pallavgrover.daresaydigital.data.model.Weather;

import java.util.List;


public interface DataSource {


    LiveData<Weather> getWeatherList(String input);

    LiveData<Boolean> isLoadingWeather();
}