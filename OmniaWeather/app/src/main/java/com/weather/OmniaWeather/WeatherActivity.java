package com.weather.OmniaWeather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;


public class WeatherActivity extends AppCompatActivity {

    TextView city_field, details_field, current_temp_field, humidity_field, pressure_field, weather_icon, time_field, wind_field, visibility_field;
    Typeface weatherFont;


    public void excute()
    {
        weatherFont = Typeface.createFromAsset(getAssets(), "fonts/weathericons-regular-webfont.ttf"); //file for icon weather
        city_field = (TextView) findViewById(R.id.City_field);
        time_field = (TextView) findViewById(R.id.TimeUpdated_field);
        details_field = (TextView) findViewById(R.id.Details_field);
        current_temp_field = (TextView) findViewById(R.id.CurrentTemp_field);
        humidity_field = (TextView) findViewById(R.id.Humidity_field);
        pressure_field = (TextView) findViewById(R.id.Pressure_field);
        weather_icon = (TextView) findViewById(R.id.Weather_Icon_field);
        weather_icon.setTypeface(weatherFont);
        wind_field =(TextView) findViewById(R.id.Wind_field);
        visibility_field= (TextView) findViewById(R.id.visibility_field);

        GetData.placeIdTask asyncTask = new GetData.placeIdTask(new GetData.AsyncResponse()
        {
            //read returned values to corresponding textviews
            public void processFinish(String weather_city, String weather_description, String weather_temperature, String weather_humidity, String weather_pressure, String weather_updatedOn, String weather_iconText, String weather_wind, String visibility)
            {
                city_field.setText(weather_city);
                time_field.setText(weather_updatedOn);
                details_field.setText(weather_description);
                current_temp_field.setText(weather_temperature);
                humidity_field.setText("Humidity: " + weather_humidity);
                pressure_field.setText("Pressure: " + weather_pressure);
                weather_icon.setText(Html.fromHtml(weather_iconText));
                wind_field.setText("Wind: "+ weather_wind);
                visibility_field.setText("Visibility: "+visibility);
            }
        });

        //get the location for the andriod device permission grantees
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        String lon =Double.toString(longitude);
        String lat =Double.toString(latitude);
        asyncTask.execute(lat, lon); //
        //59.3293° N (LAT)  , 18.0686° E (LONG) Stockholm
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //making the activity fullscreen
        setContentView(R.layout.activity_weather);
        //check permission grantees for connected andriod mobile
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else {
            excute();
        }
    }

    // wait acceptance for the permission to get location of the andriod mobile
   @Override
   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        if (grantResults.length > 0  && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            excute();
        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
        }
        return;
    }
}
