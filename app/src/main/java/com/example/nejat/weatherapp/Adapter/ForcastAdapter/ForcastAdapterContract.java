package com.example.nejat.weatherapp.Adapter.ForcastAdapter;

import com.example.nejat.weatherapp.POJO.ListData;

public class ForcastAdapterContract {
    interface ForecastRowView {

        void setView(String date,String tempMax,String tempMin, String icon, ListData listData,IItemClickListener listener);
    }
    interface ForecastAdapterPresenter{
        void onBindForecastRowViewAtPosition(int position, ForecastViewHolder rowView);
        int ForecastRowsCount();
    }
    interface ForecastAdapterModel{
        void fetchWeatherAPI();
    }


}
