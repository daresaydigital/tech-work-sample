package com.example.android.myhometownweather;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherFragment extends Fragment {
    ArrayList<Weather> mWeathers;
    Weather currentWeather;
    public static WeatherFragment newInstance(ArrayList<Weather> weathers){
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("weathers",weathers);
        weatherFragment.setArguments(args);
        return weatherFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeathers = getArguments()!=null? getArguments().<Weather>getParcelableArrayList("weathers"):null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View weather = inflater.inflate(R.layout.weather_fragment,container,false);
        LinearLayout layout = weather.findViewById(R.id.weather_fragment_layout);
        TextView mLocationTv, mTemperatureTv, mMinTempTv,mMainDescriptionTv,mFunnyCommentTv, mWindSpeedTv, mPressureTv,mHumidityTv;
        ImageView mWeatherIconV, mFunnyCommentIconV;
        mLocationTv = weather.findViewById(R.id.location_tv);
        mTemperatureTv = weather.findViewById(R.id.temperature_tv);
        mMinTempTv = weather.findViewById(R.id.min_temperature_tv);
        mMainDescriptionTv = weather.findViewById(R.id.main_description_tv);
        //todo
        mFunnyCommentIconV = weather.findViewById(R.id.funny_comments_icon);
        mFunnyCommentTv = weather.findViewById(R.id.funny_comments);
        mWeatherIconV = weather.findViewById(R.id.weather_icon);
        mWindSpeedTv= weather.findViewById(R.id.wind_speed);
        mPressureTv= weather.findViewById(R.id.pressure);
        mHumidityTv= weather.findViewById(R.id.humidity);
        if (mWeathers!=null){
            currentWeather = mWeathers.get(0);
            //location
            mLocationTv.setText(currentWeather.getmCity()+","+currentWeather.getmCountry());
            //temperature
            int minTemp = (int) Math.round(currentWeather.getmMinTemp());
            int currentTemp =(int)Math.round(currentWeather.getmTemperature());
            mTemperatureTv.setText(String.valueOf(currentTemp)+"°"+"/");
            mMinTempTv.setText(String.valueOf(minTemp)+"°");
            //funny comment
            int commentIconId = WeatherDataTransUtils.transformTempToIcon(currentTemp);
            String comment = WeatherDataTransUtils.transformTempToComment(getContext(),currentTemp);
            mFunnyCommentTv.setText(comment);
            mFunnyCommentIconV.setImageResource(commentIconId);
            //weather
            mMainDescriptionTv.setText(currentWeather.getmDescription());
            //icon
            int id = currentWeather.getmIconId();
            int iconResId = WeatherDataTransUtils.transformIdToLargeImage(id);
            mWeatherIconV.setImageResource(iconResId);
            //wind speed
            double speed = currentWeather.getmWindSpeed();
            mWindSpeedTv.setText(String.valueOf(speed)+" m/s");
            //pressure
            double pressure = currentWeather.getmPressure();
            mPressureTv.setText(String.valueOf(pressure)+" hPa");
            //humidity
            mHumidityTv.setText(String.valueOf(currentWeather.getmHumidity())+" %");
            //fragment background
            int backgroundId = WeatherDataTransUtils.transformIdToBackground(id);
            layout.setBackgroundResource(backgroundId);
        }
        return weather;
    }
}
