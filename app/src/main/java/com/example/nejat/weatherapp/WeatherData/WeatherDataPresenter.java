package com.example.nejat.weatherapp.WeatherData;

import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.example.nejat.weatherapp.POJO.ListData;
import com.example.nejat.weatherapp.Utils.ISettings;
import com.example.nejat.weatherapp.Utils.settings;

import java.io.IOException;
import java.util.List;

public class WeatherDataPresenter implements
        WeatherDataContract.WeatherDataModel.FetchData, WeatherDataContract.WeatherDataPresenter, WeatherDataContract.WeatherDataModel.FetchSeekBarData, ISettings.googleAPIAvailable {

    WeatherDataModel weatherDataModel;
    WeatherDataActivity weatherDataActivity;
    Activity activity;
    settings settings;

    public WeatherDataPresenter(WeatherDataActivity weatherDataActivity, Activity activity) {
        this.weatherDataActivity = weatherDataActivity;
        weatherDataModel = new WeatherDataModel();
        this.activity = activity;
        settings = new settings(this.activity);

    }

    @Override
    public void googleAPIAvailable() {
        weatherDataActivity.getLocationPermission();
        settings.checkLocationSetting();

    }

    @Override
    public void googleAPINotAvai() {

    }


    @Override
    public void onFetchDataSuccess(List<ListData> listData, List<ListData> dailyData) {

//        weatherDataActivity.displayDataNow(dailyData.get(0));
        weatherDataActivity.displaySeekBarData(dailyData);
        weatherDataActivity.displayWeeklyData(listData);

    }

    @Override
    public void onFetchDataFailure() {

    }

    @Override
    public void getWeatherData(double lat, double lon) {
        weatherDataModel.fetchWeeklyData(lat, lon, this);
    }

    @Override
    public void checkGoogleAPI() {
        settings.isGoogleAPIAvailable(this);
    }

    @Override
    public void progressChange(String[] arr, int progress, List<ListData> dailyDataPre) {
        String hour = null;
        switch (progress) {
            case 0:
                hour = arr[0];
                break;
            case 25:
                hour = arr[1];
                break;
            case 50:
                hour = arr[2];
                break;
            case 75:
                hour = arr[3];
                break;
            case 100:
                hour = arr[4];
                break;


        }
        ListData listData = null;
        for (int i = 0; i < dailyDataPre.size(); i++) {
            if (dailyDataPre.get(i).getDate().substring(11, 16).equals(hour)) {
                String date = dailyDataPre.get(i).getDate();
                double temp = dailyDataPre.get(i).getTemp();
                int icon = dailyDataPre.get(i).getIcon();
                double speed = dailyDataPre.get(i).getSpeed();
                String weather_desc = dailyDataPre.get(i).getWeather_desc();
                double temp_max = dailyDataPre.get(i).getTemp_max();
                double temp_min = dailyDataPre.get(i).getTemp_min();
                String city = dailyDataPre.get(i).getCityName();
                listData = new ListData(date, temp, speed, weather_desc, icon, temp_max, temp_min, city);
            }
        }
        weatherDataActivity.displayDataNow(listData);


    }

    public void getDaily(String date) {
        getSeekBarData(weatherDataModel.getDailyData(date));
    }

    @Override
    public void getSeekBarData(List<ListData> listData) {
        weatherDataActivity.displaySeekBarData(listData);
        }

    @Override
    public void getDataByLocation(String location, Geocoder geocoder) {
        try {
            List<Address> address = geocoder.getFromLocationName(location, 1);
            if (address.size() == 0) {
                weatherDataActivity.getDeviceLocation();
                Toast.makeText(activity,"The address was not valid",Toast.LENGTH_LONG).show();
            } else
                weatherDataModel.fetchWeeklyData(address.get(0).getLatitude(), address.get(0).getLongitude(), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFetchSeekBarData(List<ListData> listData) {
    }
}
