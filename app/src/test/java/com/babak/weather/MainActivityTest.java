package com.babak.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.babak.weather.ui.MainActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.fakes.RoboMenuItem;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

    @Test
    public void testSharedPrefIsEmpty() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

        assertThat(sharedPref.contains(activity.getString(R.string.sp_scale_key)), equalTo(false));
    }

    @Test
    public void testSharedPrefAfterMenuItemScaleClick() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);

        MenuItem item = new RoboMenuItem(R.id.main_toolbar_switch_scale);
        activity.onOptionsItemSelected(item);

        assertThat(sharedPref.contains(activity.getString(R.string.sp_scale_key)), equalTo(true));

        // We should be using fahrenheit at this point, i.e. the value(isCelsius) stored in sp should be false.
        assertThat(sharedPref.getBoolean(activity.getString(R.string.sp_scale_key), true),
                equalTo(false));

        activity.onOptionsItemSelected(item);

        // And back to celsius, so value should now be true.
        assertThat(sharedPref.getBoolean(activity.getString(R.string.sp_scale_key), false),
                equalTo(true));
    }

    @Test
    public void testScaleMenuItemTitleBeforeClick() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        MenuItem item = ((Toolbar)activity.findViewById(R.id.main_activity_toolbar))
                .getMenu().findItem(R.id.main_toolbar_switch_scale);

        assertThat(item.getTitle().toString(),
                equalTo(activity.getString(R.string.toolbar_switch_scale_celsius)));
    }

    @Test
    public void testScaleMenuItemTitleAfterClick() {
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);

        MenuItem item = ((Toolbar)activity.findViewById(R.id.main_activity_toolbar))
                .getMenu().findItem(R.id.main_toolbar_switch_scale);

        activity.onOptionsItemSelected(item);

        assertThat(item.getTitle().toString(),
                equalTo(activity.getString(R.string.toolbar_switch_scale_fahrenheit)));
    }

}
