package com.midnight.weatherforecast.utils;

import com.midnight.weatherforecast.R;

import java.util.HashMap;

public class AppConstant {
    public static final String Clouds="Clouds";
    public static final String Clear="Clear";
    public static final String Tornado="Tornado";
    public static final String Squall="Squall";
    public static final String Ash="Ash";
    public static final String Sand="Sand";
    public static final String Fog="Fog";
    public static final String Dust="Dust";
    public static final String Haze="Haze";
    public static final String Smoke="Smoke";
    public static final String Mist="Mist";
    public static final String Snow="Snow";
    public static final String Rain="Rain";
    public static final String Drizzle="Drizzle";
    public static final String Thunderstorm="Thunderstorm";

    public static final HashMap<String,Integer> iconList;
    static {
        iconList=new HashMap<>();
        iconList.put(Clouds, R.drawable.ic_wi_cloud);
        iconList.put(Clear, R.drawable.ic_wi_day_sunny);
        iconList.put(Tornado, R.drawable.ic_wi_lightning);
        iconList.put(Squall, R.drawable.ic_wi_day_light_wind);
        iconList.put(Ash, R.drawable.ic_wi_day_light_wind);
        iconList.put(Sand, R.drawable.ic_wi_day_light_wind);
        iconList.put(Fog, R.drawable.ic_wi_cloudy_windy);
        iconList.put(Dust, R.drawable.ic_wi_cloudy_windy);
        iconList.put(Haze, R.drawable.ic_wi_cloudy_windy);
        iconList.put(Smoke, R.drawable.ic_wi_day_light_wind);
        iconList.put(Mist, R.drawable.ic_wi_cloudy_windy);
        iconList.put(Snow, R.drawable.ic_wi_day_snow_wind);
        iconList.put(Rain, R.drawable.ic_wi_day_rain);
        iconList.put(Drizzle, R.drawable.ic_wi_day_rain);
        iconList.put(Thunderstorm, R.drawable.ic_wi_lightning);
    }

    public static final HashMap<String,Integer> iconListBlack;
    static {
        iconListBlack=new HashMap<>();
        iconListBlack.put(Clouds, R.drawable.ic_wi_cloud_black);
        iconListBlack.put(Clear, R.drawable.ic_wi_day_sunny_black);
        iconListBlack.put(Tornado, R.drawable.ic_wi_lightning_black);
        iconListBlack.put(Squall, R.drawable.ic_wi_day_light_wind_black);
        iconListBlack.put(Ash, R.drawable.ic_wi_day_light_wind_black);
        iconListBlack.put(Sand, R.drawable.ic_wi_day_light_wind_black);
        iconListBlack.put(Fog, R.drawable.ic_wi_cloudy_windy_black);
        iconListBlack.put(Dust, R.drawable.ic_wi_cloudy_windy_black);
        iconListBlack.put(Haze, R.drawable.ic_wi_cloudy_windy_black);
        iconListBlack.put(Smoke, R.drawable.ic_wi_day_light_wind_black);
        iconListBlack.put(Mist, R.drawable.ic_wi_cloudy_windy_black);
        iconListBlack.put(Snow, R.drawable.ic_wi_day_snow_wind_black);
        iconListBlack.put(Rain, R.drawable.ic_wi_day_rain_black);
        iconListBlack.put(Drizzle, R.drawable.ic_wi_day_rain_black);
        iconListBlack.put(Thunderstorm, R.drawable.ic_wi_lightning_black);
    }

    public static final HashMap<String,Integer> iconListMight;
    static {
        iconListMight=new HashMap<>();
        iconListMight.put(Clouds, R.drawable.ic_wi_night_cloudy);
        iconListMight.put(Clear, R.drawable.ic_wi_night_clear);
        iconListMight.put(Tornado, R.drawable.ic_wi_lightning);
        iconListMight.put(Squall, R.drawable.ic_wi_night_alt_cloudy_windy);
        iconListMight.put(Ash, R.drawable.ic_wi_night_alt_cloudy_windy);
        iconListMight.put(Sand, R.drawable.ic_wi_night_alt_cloudy_windy);
        iconListMight.put(Fog, R.drawable.ic_wi_cloudy_windy);
        iconListMight.put(Dust, R.drawable.ic_wi_cloudy_windy);
        iconListMight.put(Haze, R.drawable.ic_wi_cloudy_windy);
        iconListMight.put(Smoke, R.drawable.ic_wi_night_alt_cloudy_windy);
        iconListMight.put(Mist, R.drawable.ic_wi_night_alt_cloudy_windy);
        iconListMight.put(Snow, R.drawable.ic_wi_night_alt_snow);
        iconListMight.put(Rain, R.drawable.ic_wi_night_alt_rain);
        iconListMight.put(Drizzle, R.drawable.ic_wi_night_cloudy);
        iconListMight.put(Thunderstorm, R.drawable.ic_wi_lightning);
    }


    public static final HashMap<String,Integer> colorList;
    static {
        colorList=new HashMap<>();
        colorList.put(Clouds, 0xFF6D5DF1);
        colorList.put(Clear, 0xFFFFC107);
        colorList.put(Tornado, 0xFF757575);
        colorList.put(Squall, 0xFF757575);
        colorList.put(Ash, 0xFF9E9E9E);
        colorList.put(Sand, 0xFF795548);
        colorList.put(Fog,0xFFF5F5F5);
        colorList.put(Dust, 0xFF455A64);
        colorList.put(Haze, 0xFFBDBDBD);
        colorList.put(Smoke, 0xFFBDBDBD);
        colorList.put(Mist, 0xFFBDBDBD);
        colorList.put(Snow, 0xFFFFFFFF);
        colorList.put(Rain, 0xFF0097A7);
        colorList.put(Drizzle, 0xFF00BCD4);
        colorList.put(Thunderstorm, 0xFF303F9F);
    }

    public static final HashMap<Integer,String> dayOfWeek;
    static {
        dayOfWeek=new HashMap<>();
        dayOfWeek.put(1, "Saturday");
        dayOfWeek.put(2, "Sunday");
        dayOfWeek.put(3, "Monday");
        dayOfWeek.put(4, "Tuesday");
        dayOfWeek.put(5, "Wednesday");
        dayOfWeek.put(6, "Thursday");
        dayOfWeek.put(7, "Friday");
    }

    public static String DB_NAME="weatherDB.db";
}
