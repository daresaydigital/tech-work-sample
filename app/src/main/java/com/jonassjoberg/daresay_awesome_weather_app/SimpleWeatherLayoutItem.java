package com.jonassjoberg.daresay_awesome_weather_app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.textclassifier.TextClassification;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SimpleWeatherLayoutItem extends View {

    private View mView;
    private float mTemp;
    private String mIcon;

    private TextView mTextViewTemp;
    private ImageView mImageViewIcon;

    public SimpleWeatherLayoutItem(Context context, float temp, String icon) {
        super(context);

        mTemp = temp;
        mIcon = icon;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }
}
