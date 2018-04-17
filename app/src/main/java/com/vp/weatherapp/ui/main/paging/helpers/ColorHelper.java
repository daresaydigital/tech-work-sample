package com.vp.weatherapp.ui.main.paging.helpers;

import android.graphics.Color;


public class ColorHelper {

    public static int blendColors(final int color1, final int color2, final float ratio) {
        if (ratio < 0 || ratio > 1) {
            throw new IllegalArgumentException("ratio must be between 0 and 1 (inclusive)");
        }

        final float inverseRatio = 1f - ratio;

        final float a = (Color.alpha(color1) * inverseRatio) + (Color.alpha(color2) * ratio);
        final float r = (Color.red(color1) * inverseRatio) + (Color.red(color2) * ratio);
        final float g = (Color.green(color1) * inverseRatio) + (Color.green(color2) * ratio);
        final float b = (Color.blue(color1) * inverseRatio) + (Color.blue(color2) * ratio);

        return Color.argb((int) a, (int) r, (int) g, (int) b);
    }

}