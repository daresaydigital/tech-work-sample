package com.example.nejat.weatherapp.WeatherData;

import android.util.Log;

import com.example.nejat.weatherapp.API.IRetrofitClientInstance;
import com.example.nejat.weatherapp.API.RetrofitClientInstance;
import com.example.nejat.weatherapp.POJO.Forecast;
import com.example.nejat.weatherapp.POJO.ListData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherDataModel implements WeatherDataContract.WeatherDataModel {
    List<ListData> weeklyWeathers;
    List<ListData> listData;
    List<ListData> dailyData;
    String date = null;

    @Override
    public void fetchWeeklyData(double lat, double lon, final FetchData fetchWeatherData) {
        IRetrofitClientInstance service = RetrofitClientInstance.getRetrofitInstance().create(IRetrofitClientInstance.class);
        Call<Forecast> call = service.getForecastData(lat, lon);
        Log.i("DAteis ", call.toString() + "");

        call.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Call<Forecast> call, Response<Forecast> response) {
                if (response.isSuccessful()) {

                    int count = response.body().getCount();
                    weeklyWeathers = new ArrayList<>();
                    listData = new ArrayList<>();
                    dailyData = new ArrayList<>();

                    String i = response.body().getList().get(0).getDate();
                    String city = response.body().getCity().getName();
                    double temp = response.body().getList().get(0).getWeatherDetail().getTemprature();
                    double speed = response.body().getList().get(0).getWind().getSpeed();
                    String weather_desc = response.body().getList().get(0).getWeatherDesc().get(0).getDescription();
                    int icon = response.body().getList().get(0).getWeatherDesc().get(0).getIcon();
                    double temp_max = response.body().getList().get(0).getWeatherDetail().getTempratureMax();
                    double temp_min = response.body().getList().get(0).getWeatherDetail().getTempratureMin();
                    weeklyWeathers.add(new ListData(i, temp, speed, weather_desc, icon, temp_max, temp_min,city));

                    getCompleteData(count, response);
                    getWeeklyData(count, response);
                    getDailyData(date);

                    fetchWeatherData.onFetchDataSuccess(weeklyWeathers, dailyData);

                }
            }

            @Override
            public void onFailure(Call<Forecast> call, Throwable t) {
                Log.i("DAte is error", t.toString());

            }
        });

    }

    public void getWeeklyData(int count, Response<Forecast> response) {
        int x = 0;
        for (int cnt = 0; cnt < count; cnt++) {
            if (weeklyWeathers != null && !(weeklyWeathers.get(x).getDate().substring(0, 10).equals(listData.get(cnt).getDate().substring(0, 10)))) {
                x++;
                String i3 = response.body().getList().get(cnt).getDate();
                double temp3 = response.body().getList().get(cnt).getWeatherDetail().getTemprature();
                double speed3 = response.body().getList().get(cnt).getWind().getSpeed();
                String weather_desc = response.body().getList().get(cnt).getWeatherDesc().get(0).getDescription();
                int icon = response.body().getList().get(cnt).getWeatherDesc().get(0).getIcon();
                double temp_max = response.body().getList().get(cnt).getWeatherDetail().getTempratureMax();
                double temp_min = response.body().getList().get(cnt).getWeatherDetail().getTempratureMin();
                String city = response.body().getCity().getName();
                weeklyWeathers.add(new ListData(i3, temp3, speed3, weather_desc, icon, temp_max, temp_min,city));

            }
        }
    }

    public void getCompleteData(int count, Response<Forecast> response) {
        for (int cnt = 0; cnt < count; cnt++) {

            String i2 = response.body().getList().get(cnt).getDate();
            double temp2 = response.body().getList().get(cnt).getWeatherDetail().getTemprature();
            double speed2 = response.body().getList().get(cnt).getWind().getSpeed();
            String weather_desc = response.body().getList().get(cnt).getWeatherDesc().get(0).getDescription();
            int icon = response.body().getList().get(cnt).getWeatherDesc().get(0).getIcon();
            double temp_max = response.body().getList().get(cnt).getWeatherDetail().getTempratureMax();
            double temp_min = response.body().getList().get(cnt).getWeatherDetail().getTempratureMin();
            String city = response.body().getCity().getName();
            Log.i("date", i2 + "  " + temp2 + "  " + speed2 + "  ");
            listData.add(new ListData(i2, temp2, speed2, weather_desc, icon, temp_max, temp_min,city));
        }
    }

    public List<ListData> getDailyData(String date) {
        String currentDateandTime;
        if (date == null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
            currentDateandTime = sdf.format(new Date());
            return seekBarData(currentDateandTime);
        } else {
            dailyData.clear();
            currentDateandTime = date;
            return seekBarData(currentDateandTime);


        }


    }

    public List<ListData> seekBarData(String currentDateandTime) {
        for (int i = 0; i < listData.size(); i++) {
            if (listData.get(i).getDate().substring(0, 10).equals(currentDateandTime.substring(0, 10))) {
                dailyData.add(listData.get(i));
            }


        }
        return dailyData;
    }

    @Override
    public void fetchSeekBarData(List<ListData> listData, FetchSeekBarData fetchSeekBarData) {
        fetchSeekBarData.onFetchSeekBarData(listData);
    }

}
