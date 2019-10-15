package com.midnight.weatherforecast.utils;

import android.text.format.DateFormat;
import android.util.Log;

import com.midnight.weatherforecast.core.Loader;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Utils {
    /**
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable){
        Loader.applicationHandler.post(runnable);
    }

    public static int getHourOfDay(int date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(Long.valueOf(date)*1000);
        return calendar.get(Calendar.HOUR_OF_DAY);

    }

    public static String getDayOfWeek(int date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        calendar.setTimeInMillis(Long.valueOf(date)*1000);
        return AppConstant.dayOfWeek.get(calendar.get(Calendar.DAY_OF_WEEK));

    }

    public static String getDateFormat(int date){
        Log.e("date","->" + date);
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(Long.valueOf(date)*1000);
        return DateFormat.format("HH:mm", cal).toString();

    }

}
