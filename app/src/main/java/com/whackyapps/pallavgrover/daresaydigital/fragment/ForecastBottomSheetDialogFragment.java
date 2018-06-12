package com.whackyapps.pallavgrover.daresaydigital.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.whackyapps.pallavgrover.daresaydigital.R;
import com.whackyapps.pallavgrover.daresaydigital.data.model.WeatherForecast;
import com.whackyapps.pallavgrover.daresaydigital.util.Util;

import java.util.Locale;

public class ForecastBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private WeatherForecast.WeatherForecastOneDay mWeather;

    public ForecastBottomSheetDialogFragment newInstance(WeatherForecast.WeatherForecastOneDay weather) {
        ForecastBottomSheetDialogFragment fragment = new ForecastBottomSheetDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("weatherForecast", weather);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWeather = (WeatherForecast.WeatherForecastOneDay) getArguments().getSerializable("weatherForecast");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_forecast_bottom_sheet, parent, false);

        String speedScale = Util.getSpeedScale(getActivity());
        String percentSign = getActivity().getString(R.string.percent_sign);
        String pressureMeasurement = getActivity().getString(R.string.pressure_measurement);
        String mmLabel = getString(R.string.millimetre_label);

        Float temperatureMorning = mWeather.getTemprature().getMorn();
        Float temperatureDay =mWeather.getTemprature().getDay();
        Float temperatureEvening = mWeather.getTemprature().getEve();
        Float temperatureNight = mWeather.getTemprature().getNight();

        String description = mWeather.getWeatherInfs().get(0).getDescription();
        String temperatureMorningStr = getActivity().getString(R.string.temperature_with_degree,
                                                               String.format(Locale.getDefault(),
                                                                             "%.0f",
                                                                             temperatureMorning));
        String temperatureDayStr = getActivity().getString(R.string.temperature_with_degree,
                                                           String.format(Locale.getDefault(),
                                                                         "%.0f",
                                                                         temperatureDay));
        String temperatureEveningStr = getActivity().getString(R.string.temperature_with_degree,
                                                               String.format(Locale.getDefault(),
                                                                             "%.0f",
                                                                             temperatureEvening));
        String temperatureNightStr = getActivity().getString(R.string.temperature_with_degree,
                                                             String.format(Locale.getDefault(),
                                                                           "%.0f",
                                                                           temperatureNight));
        String wind = getActivity().getString(R.string.wind_label, mWeather.getSpeed(), speedScale);
        long windDegree = mWeather.getDeg();
//        String windDirection = Util.windDegreeToDirections(getActivity(),
//                                                            Double.parseDouble(windDegree));
        String rain = getString(R.string.rain_label, mWeather.getRain(), mmLabel);
//        String snow = getString(R.string.snow_label, mWeather.getSnow(), mmLabel);
        String pressure = getActivity().getString(R.string.pressure_label, mWeather.getPressure(), pressureMeasurement);
        String humidity = getActivity().getString(R.string.humidity_label, mWeather.getHumidity(), percentSign);

        TextView descriptionView = (TextView) v.findViewById(R.id.forecast_description);
        TextView windView = (TextView) v.findViewById(R.id.forecast_wind);
        TextView rainView = (TextView) v.findViewById(R.id.forecast_rain);
        TextView snowView = (TextView) v.findViewById(R.id.forecast_snow);
        TextView humidityView = (TextView) v.findViewById(R.id.forecast_humidity);
        TextView pressureView = (TextView) v.findViewById(R.id.forecast_pressure);

        TextView temperatureMorningView = (TextView) v.findViewById(
                R.id.forecast_morning_temperature);
        TextView temperatureDayView = (TextView) v.findViewById(
                R.id.forecast_day_temperature);
        TextView temperatureEveningView = (TextView) v.findViewById(
                R.id.forecast_evening_temperature);
        TextView temperatureNightView = (TextView) v.findViewById(
                R.id.forecast_night_temperature);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(),
                                                     "fonts/weathericons-regular-webfont.ttf");

        descriptionView.setText(description);
        windView.setTypeface(typeface);
//        windView.setText(wind + " " + windDirection);
        rainView.setText(rain);
//        snowView.setText(snow);
        humidityView.setText(humidity);
        pressureView.setText(pressure);
        if (temperatureMorning > 0) {
            temperatureMorningStr = "+" + temperatureMorningStr;
        }
        if (temperatureDay > 0) {
            temperatureDayStr = "+" + temperatureDayStr;
        }
        if (temperatureEvening > 0) {
            temperatureEveningStr = "+" + temperatureEveningStr;
        }
        if (temperatureNight > 0) {
            temperatureNightStr = "+" + temperatureNightStr;
        }
        temperatureMorningView.setText(temperatureMorningStr);
        temperatureDayView.setText(temperatureDayStr);
        temperatureEveningView.setText(temperatureEveningStr);
        temperatureNightView.setText(temperatureNightStr);

        return v;
    }
}
