package com.example.nejat.weatherapp.WeatherData;

import android.location.Geocoder;

import com.example.nejat.weatherapp.POJO.ListData;

import java.util.List;

public class WeatherDataContract {

    public interface WeatherDataView{
        void displayWeeklyData(List<ListData> listData);
       void displayDataNow(ListData listData);
       void displaySeekBarData(List<ListData> dailyData);

   }
    interface WeatherDataModel{
        interface FetchData{
            void onFetchDataSuccess(List<ListData> listData, List<ListData> dailyData);
            void onFetchDataFailure();
        }
        interface FetchSeekBarData{
            void onFetchSeekBarData(List<ListData> listData);
        }


        void fetchWeeklyData(double lat, double lon,FetchData fetchWeatherData);
        void fetchSeekBarData(List<ListData> listData,FetchSeekBarData fetchSeekBarData);
    }
    interface WeatherDataPresenter{
        void getWeatherData(double lat, double lon);
        void checkGoogleAPI();
        void progressChange(String[] arr,int progress,List<ListData> dailyDataPre);
        void getSeekBarData(List<ListData> listData);
        void getDataByLocation(String location, Geocoder geocoder);
    }
}
