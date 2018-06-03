package com.example.android.myhometownweather;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WeatherDataTransUtils {
    private WeatherDataTransUtils(){
    }
    public static String transformDate(long dateToTransform){
        SimpleDateFormat formatter = new SimpleDateFormat("EEE");
        Date date = new Date(dateToTransform*1000);
        String dayOfWeek = formatter.format(date);
        return dayOfWeek;
    }

    public static int transformIdToLargeImage(int weatherId){
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.storm_l;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.light_rain_l;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.rain_l;
        } else if (weatherId == 511) {
            return R.drawable.snowy_l;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.rain_l;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.snowy_l;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.fog_l;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.storm_l;
        } else if (weatherId == 800) {
            return R.drawable.sunny_l;
        } else if (weatherId == 801) {
            return R.drawable.light_cloudy_l;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.clouds_l;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.storm_l;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.storm_l;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.sunny_l;
        }

        return R.drawable.storm_l;
    }

    public static int transformIdToSmallImage(int weatherId){
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.storm_s;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.light_rain_s;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.rain_s;
        } else if (weatherId == 511) {
            return R.drawable.snowy_s;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.rain_s;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.snowy_s;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.fog_s;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.storm_s;
        } else if (weatherId == 800) {
            return R.drawable.sunny_s;
        } else if (weatherId == 801) {
            return R.drawable.light_cloudy_s;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.light_rain_s;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.storm_s;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.storm_s;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.sunny_s;
        }
        return R.drawable.storm_s;
    }
    public static int transformIdToBackground(int weatherId){
        if (weatherId >= 200 && weatherId <= 232) {
            return R.drawable.storm_bg;
        } else if (weatherId >= 300 && weatherId <= 321) {
            return R.drawable.light_rain_bg;
        } else if (weatherId >= 500 && weatherId <= 504) {
            return R.drawable.rain_bg;
        } else if (weatherId == 511) {
            return R.drawable.snowy_bg;
        } else if (weatherId >= 520 && weatherId <= 531) {
            return R.drawable.rain_bg;
        } else if (weatherId >= 600 && weatherId <= 622) {
            return R.drawable.snowy_bg;
        } else if (weatherId >= 701 && weatherId <= 761) {
            return R.drawable.fog_bg;
        } else if (weatherId == 761 || weatherId == 771 || weatherId == 781) {
            return R.drawable.storm_bg;
        } else if (weatherId == 800) {
            return R.drawable.sunny_bg;
        } else if (weatherId == 801) {
            return R.drawable.light_cloud_bg;
        } else if (weatherId >= 802 && weatherId <= 804) {
            return R.drawable.cloud_bg;
        } else if (weatherId >= 900 && weatherId <= 906) {
            return R.drawable.storm_bg;
        } else if (weatherId >= 958 && weatherId <= 962) {
            return R.drawable.storm_bg;
        } else if (weatherId >= 951 && weatherId <= 957) {
            return R.drawable.sunny_bg;
        }
        return R.drawable.storm_bg;
    }

    public static int transformTempToIcon(int temperature){
        int activityId = R.drawable.walking_with_dog;
        if (temperature>=35){
            activityId = R.drawable.ice_cream;
        }else if (temperature >= 30){
            activityId = R.drawable.sunbed;
        }else if (temperature >=25){
            activityId = R.drawable.walking_with_dog;
        }else if (temperature >= 20){
            activityId = R.drawable.flowers;
        }else if (temperature >=15){
            activityId = R.drawable.park;
        }else if (temperature >=10){
            activityId = R.drawable.scarf;
        }else if (temperature >=5){
            activityId = R.drawable.jacket;
        }else if (temperature >= 0){
            activityId = R.drawable.fireplace;
        }else if (temperature >= -5){
            activityId = R.drawable.spa;
        }else if (temperature >= -10){
            activityId = R.drawable.snowman;
        }else if (temperature >=-15){
            activityId = R.drawable.ski;
        }
        return activityId;
    }

    public static String transformTempToComment(Context context, int temperature){
        String commentId = context.getString(R.string.twenty_five);
        if (temperature>=35){
            commentId = context.getString(R.string.thirty_five);
        }else if (temperature >= 30){
            commentId = context.getString(R.string.thirty);
        }else if (temperature >=25){
            commentId = context.getString(R.string.twenty_five);
        }else if (temperature >= 20){
            commentId = context.getString(R.string.twenty);
        }else if (temperature >=15){
            commentId = context.getString(R.string.fifteen);
        }else if (temperature >=10){
            commentId = context.getString(R.string.ten);
        }else if (temperature >=5){
            commentId = context.getString(R.string.five);
        }else if (temperature >= 0){
            commentId = context.getString(R.string.zero);
        }else if (temperature >= -5){
            commentId = context.getString(R.string.minus_five);
        }else if (temperature >= -10){
            commentId =context.getString(R.string.minus_ten);
        }else if (temperature >=-15){
            commentId = context.getString(R.string.minus_fifteen);
        }
        return commentId;
    }
}
