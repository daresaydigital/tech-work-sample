package com.babak.weather;


import android.widget.ImageView;
import android.widget.TextView;

import com.babak.weather.models.Main;
import com.babak.weather.models.Weather;
import com.babak.weather.models.WeatherResponse;
import com.babak.weather.models.Wind;
import com.babak.weather.ui.WeatherFragment;
import com.babak.weather.utils.ConversionUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import java.util.ArrayList;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(RobolectricTestRunner.class)
public class WeatherFragmentTest {
    private WeatherFragment fragment;
    private WeatherResponse weatherData;

    @Before
    public void setup() throws Exception {
        weatherData = new WeatherResponse();
        weatherData.setName("Stockholm");
        Weather weather = new Weather();
        Wind wind = new Wind();
        Main main = new Main();
        weather.setDescription("Hurricane");
        weather.setIcon("902d");
        weather.setId(902);
        wind.setDeg(180.0);
        wind.setSpeed(2.3);
        main.setHumidity(70);
        main.setPressure(44.4);
        main.setTemp(35.1);
        main.setTempMax(50.2);
        main.setTempMin(25.3);
        ArrayList<Weather> weatherArray = new ArrayList<Weather>();
        weatherArray.add(weather);

        weatherData.setWeather(weatherArray);
        weatherData.setMain(main);
        weatherData.setWind(wind);

        fragment = WeatherFragment.newInstance(weatherData);
        startFragment(fragment);

    }

    @Test
    public void testSetupImages() {

        ImageView humidity = (ImageView) fragment.getView().findViewById(R.id.weather_fragment_humidity_image);
        ImageView main = (ImageView) fragment.getView().findViewById(R.id.weather_fragment_main_image);
        ImageView pressure = (ImageView) fragment.getView().findViewById(R.id.weather_fragment_pressure_image);
        ImageView windDirection = (ImageView) fragment.getView().findViewById(R.id.weather_fragment_wind_direction_image);
        ImageView wind = (ImageView) fragment.getView().findViewById(R.id.weather_fragment_wind_image);

        assertThat(humidity.getDrawable().getCurrent(), not(equalTo(null)));
        assertThat(main.getDrawable().getCurrent(), not(equalTo(null)));
        assertThat(pressure.getDrawable().getCurrent(), not(equalTo(null)));
        assertThat(windDirection.getDrawable().getCurrent(), not(equalTo(null)));
        assertThat(wind.getDrawable().getCurrent(), not(equalTo(null)));

        assertEquals(windDirection.getRotation(), 180.0, 0.1);
    }

    @Test
    public void testSetupTexts() {
        TextView city = (TextView) fragment.getView().findViewById(R.id.weather_fragment_city_text);
        TextView description = (TextView) fragment.getView().findViewById(R.id.weather_fragment_description_text);
        TextView humidity = (TextView) fragment.getView().findViewById(R.id.weather_fragment_humidity_text);
        TextView max = (TextView) fragment.getView().findViewById(R.id.weather_fragment_max_degree_text);
        TextView min = (TextView) fragment.getView().findViewById(R.id.weather_fragment_min_degree_text);
        TextView degree = (TextView) fragment.getView().findViewById(R.id.weather_fragment_degree_text);
        TextView pressure = (TextView) fragment.getView().findViewById(R.id.weather_fragment_pressure_text);
        TextView wind = (TextView) fragment.getView().findViewById(R.id.weather_fragment_wind_text);
        TextView time = (TextView) fragment.getView().findViewById(R.id.weather_fragment_time_text);


        assertThat(city.getText().toString(), containsString("Stockholm"));
        assertThat(description.getText().toString(), containsString("Hurricane"));
        assertThat(humidity.getText().toString(), containsString("70"));
        assertThat(max.getText().toString(), containsString("50"));
        assertThat(min.getText().toString(), containsString("25"));
        assertThat(degree.getText().toString(), containsString("35"));
        assertThat(pressure.getText().toString(), containsString("44"));
        assertThat(wind.getText().toString(), containsString("2"));
        assertThat(time.getText().toString(), not(""));
    }
}