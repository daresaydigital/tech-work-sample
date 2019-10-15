package com.midnight.weatherforecast.core;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;

import androidx.annotation.NonNull;

public class Loader extends Application {
    public static volatile Handler applicationHandler;
    public static Loader ourInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance=this;
        applicationHandler = new Handler(getApplicationContext().getMainLooper());
    }

    /**
     * @return
     */
    public static Loader getInstanse() {
        if (ourInstance == null)
            ourInstance = new Loader();
        return ourInstance;
    }

    /**
     * @return
     */
    public Context getApplicationContext() {
        return Loader.this;
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
