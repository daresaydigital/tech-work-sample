package com.babak.weather.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.babak.weather.R;
import com.babak.weather.models.Main;
import com.babak.weather.models.Weather;
import com.babak.weather.models.WeatherResponse;
import com.babak.weather.utils.ConversionUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class WeatherFragment extends Fragment {
    @BindView(R.id.weather_fragment_city_text) TextView cityText;
    @BindView(R.id.weather_fragment_time_text) TextView timeOfSearchText;
    @BindView(R.id.weather_fragment_description_text) TextView descriptionText;
    @BindView(R.id.weather_fragment_humidity_text) TextView humidityText;
    @BindView(R.id.weather_fragment_wind_text) TextView windText;
    @BindView(R.id.weather_fragment_pressure_text) TextView pressureText;
    @BindView(R.id.weather_fragment_max_degree_text) TextView maxDegreeText;
    @BindView(R.id.weather_fragment_min_degree_text) TextView minDegreeText;
    @BindView(R.id.weather_fragment_degree_text) TextView degreeText;

    @BindView(R.id.weather_fragment_main_image) ImageView mainWeatherImage;
    @BindView(R.id.weather_fragment_humidity_image) ImageView humidityImage;
    @BindView(R.id.weather_fragment_pressure_image) ImageView pressureImage;
    @BindView(R.id.weather_fragment_wind_image) ImageView windImage;
    @BindView(R.id.weather_fragment_wind_direction_image) ImageView windDirectionImage;

    private WeatherResponse weatherData = null;
    private Unbinder unbinder;


    public WeatherFragment() {
    }

    public static WeatherFragment newInstance(WeatherResponse param1) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putSerializable("data", param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            weatherData = (WeatherResponse) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        unbinder = ButterKnife.bind(this, view);

        setupImages();
        setupTexts();

        return view;

    }

    private void setupImages() {
        Weather weather = weatherData.getWeather().get(0);

        String iconName = ConversionUtils.owmIdToIconName(weather.getId(), weather.getIcon());
        int resId = getResources().getIdentifier(iconName, "drawable", getActivity().getPackageName());
        mainWeatherImage.setImageResource(resId);
        if(weatherData.getWind().getDeg() != null) {
            windDirectionImage.setVisibility(View.VISIBLE);
            windDirectionImage.setRotation(Double.valueOf(weatherData.getWind().getDeg()).floatValue());
        } else {
            windDirectionImage.setVisibility(View.INVISIBLE);
        }
    }

    private void setupTexts() {
        Weather weather = weatherData.getWeather().get(0);
        Main mainInfo = weatherData.getMain();

        String humidity = Integer.toString(mainInfo.getHumidity()) + "%";
        String wind = Integer.toString((int) Math.round(weatherData.getWind().getSpeed()));
        wind = wind + " " + getString(R.string.weather_ms);
        String pressure = Integer.toString((int) Math.round(mainInfo.getPressure()));
        pressure = pressure + " " + getString(R.string.weather_mb);

        String max = Integer.toString((int) Math.round(mainInfo.getTempMax()));
        max = max + " " + getString(R.string.weather_max);
        String min = Integer.toString((int) Math.round(mainInfo.getTempMin()));
        min = min + " " + getString(R.string.weather_min);
        String temp = Integer.toString((int) Math.round(mainInfo.getTemp()));

        String time = new SimpleDateFormat("HH:mm").format(new Date());

        cityText.setText(weatherData.getName());
        timeOfSearchText.setText(time);
        // This should use the resource strings instead, but I'm too lazy to
        // add all of them right now.
        descriptionText.setText(weather.getDescription());
        humidityText.setText(humidity);
        windText.setText(wind);
        pressureText.setText(pressure);
        maxDegreeText.setText(max);
        minDegreeText.setText(min);
        degreeText.setText(temp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
