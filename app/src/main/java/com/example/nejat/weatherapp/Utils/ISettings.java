package com.example.nejat.weatherapp.Utils;

import com.example.nejat.weatherapp.POJO.ListData;
import com.example.nejat.weatherapp.WeatherData.WeatherDataContract;

import java.util.List;

public interface ISettings {
    public interface googleAPIAvailable{
        void googleAPIAvailable();
        void googleAPINotAvai();
    }
    void isGoogleAPIAvailable(googleAPIAvailable googleAPIAvailable);
}
