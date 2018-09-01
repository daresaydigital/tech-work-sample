package com.robledo.weatheri.utils;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;

public class AnimationUtils {

    public static void animateScaleShow(View v) {
        Animation scaleAnimation = new ScaleAnimation(
                0.3f, 1f, // Start and end values for the X axis scaling
                0.3f, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        scaleAnimation.setFillAfter(true); // Needed to keep the result of the animation
        scaleAnimation.setDuration(1000);
        Animation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setFillAfter(true); // Needed to keep the result of the animation
        alphaAnimation.setDuration(1000);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        v.startAnimation(set);
    }

    public static void animateAlphaShow(View v, int time) {
        Animation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setFillAfter(true); // Needed to keep the result of the animation
        alphaAnimation.setDuration(time);
        v.startAnimation(alphaAnimation);
    }

    public static void animateExpandCollapse(final View v) {
        Animation scaleAnimation = new ScaleAnimation(
                1f, 1.08f, // Start and end values for the X axis scaling
                1f, 1.08f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        scaleAnimation.setDuration(100);
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Animation scaleAnimation = new ScaleAnimation(
                        1.08f, 1f, // Start and end values for the X axis scaling
                        1.08f, 1f, // Start and end values for the Y axis scaling
                        Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                        Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
                scaleAnimation.setDuration(100);
                v.startAnimation(scaleAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        v.startAnimation(scaleAnimation);
    }
}
