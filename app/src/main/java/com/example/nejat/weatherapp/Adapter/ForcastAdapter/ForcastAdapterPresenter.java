package com.example.nejat.weatherapp.Adapter.ForcastAdapter;

import android.content.Context;

import com.example.nejat.weatherapp.POJO.ListData;
import com.example.nejat.weatherapp.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ForcastAdapterPresenter implements
        ForcastAdapterContract.ForecastAdapterPresenter {
    private List<ListData> listDataList;
    private ListData listData;
    private Context context;
    private final IItemClickListener listener;


    public ForcastAdapterPresenter(Context context, List<ListData> listDataList, IItemClickListener listener) {
        this.context = context;
        this.listDataList = listDataList;
        this.listener = listener;
    }

    public ForcastAdapterPresenter(ListData listData, Context context) {
        this.listData = listData;
        this.context = context;
        this.listener = null;
    }

    @Override
    public void onBindForecastRowViewAtPosition(int position, ForecastViewHolder rowView) {
        String date_s = listDataList.get(position).getDate();
        int idIcon = listDataList.get(position).getIcon();
        String temp = (int) listDataList.get(position).getTemp_min() + "\u00b0";
        String weather_desc =  listDataList.get(position).getWeather_desc();
        Calendar cal = Calendar.getInstance();
        rowView.setView(changeDateFormat(date_s,"EEE MMM dd"),weather_desc,temp,setWeatherIcon(idIcon, cal.get(Calendar.HOUR_OF_DAY)), listDataList.get(position),listener);

    }

    @Override
    public int ForecastRowsCount() {
        return listDataList.size();
    }

    public String setWeatherIcon(int actualId, int hourOfDay) {
        int id = actualId / 100;
        String icon = "";
        if (actualId == 800) {
            if (hourOfDay >= 7 && hourOfDay < 20) {
                icon = context.getResources().getString(R.string.weather_sunny);
            } else {
                icon = context.getResources().getString(R.string.weather_clear_night);
            }
        } else {
            switch (id) {
                case 2:
                    icon = context.getResources().getString(R.string.weather_thunder);
                    break;
                case 3:
                    icon = context.getResources().getString(R.string.weather_drizzle);
                    break;
                case 7:
                    icon = context.getResources().getString(R.string.weather_foggy);
                    break;
                case 8:
                    icon = context.getResources().getString(R.string.weather_cloudy);
                    break;
                case 6:
                    icon = context.getResources().getString(R.string.weather_snowy);
                    break;
                case 5:
                    icon = context.getResources().getString(R.string.weather_rainy);
                    break;
            }
        }
        return icon;
    }

    public String changeDateFormat(String oldDate,String pattern) {
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat dt1 = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = dt.parse(oldDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt1.format(date);
    }
}
