package com.jonassjoberg.daresay_awesome_weather_app;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomWeatherTextView extends TextView {

    private static Typeface mTypeface = null;

    public CustomWeatherTextView(Context context) {
        super(context);
        initStyle(context);
    }

    public CustomWeatherTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initStyle(context);
    }

    public CustomWeatherTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStyle(context);
    }

    public CustomWeatherTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initStyle(context);
    }

    private void initStyle(Context context) {
        if (mTypeface == null) {
            mTypeface = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/weathericons-regular-webfont.ttf");
        }

        this.setTypeface(mTypeface);
        this.setTextColor(Color.WHITE);
        this.setTextSize(50.f);
    }
}
