package com.robledo.weatheri.utils;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

public class ViewUtils {

    /**
     *  Icons for weather are not images or vectors but a font which creates them. This class
     *  gets the appropriate letter to show the icon according to the id send by the API,
     *  this id has 3 digits, we only count the first one since it does not have to be so
     *  specific.
     * @param idIcon id sent by the API of an specific type of weather
     * @return The letter to show the appropriate icon
     */
    public static String getWeatherLetterForId(Integer idIcon) {
        if (idIcon == 800) //Clear has special icon of 800 and the rest of 8** are Clouds
            return "B";
        switch (MathUtils.getFirstDigit(idIcon)) { //Only need first digit to get the icon
            case 2: return "0"; //Thunderstorm
            case 3: return "Q"; //Drizzle
            case 5: return "R"; //Rain
            case 6: return "X"; //Snow
            case 7: return "J"; //Atmosphere
            case 8: return "Y"; //Clouds
        }
        return "B";
    }

    /**
     * Set the status bar transparent, call before setcontentview
     * @param activity
     * @param makeTranslucent
     */
    public static void setStatusBarTranslucent(Activity activity, boolean makeTranslucent) {
        if (makeTranslucent) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    public static void setFullScreenActivity(Activity activity){
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
