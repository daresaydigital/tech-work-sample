package com.vp.weatherapp.ui.main.paging.helpers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;


public class DimensionHelper {

    public static int dpToPx(@NonNull final Context context, final float dpValue) {
        if (dpValue < 0) {
            throw new IllegalArgumentException("dpValue must be >= 0");
        }

        final DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, metrics);
    }
}
