package com.robledo.weatheri.interfaces;

import java.util.List;

public interface MainViewContract {

    interface View {
        void showWait();
        void removeWait();
        void onFailure(String appErrorMessage);
        void showCurrentWeather(Double temperature, String description, String idIcon);
        void showWeatherForecast(List<com.robledo.weatheri.models.List> dailyWeatherList);
        void showCityName(String cityName);
    }

    interface Presenter {
        void viewIsReady(String lastLocationCity, String countryCode);
        void getCurrentWeather(String lastLocationCity);
        void getDailyForecast(String lastLocationCity);
    }

}
