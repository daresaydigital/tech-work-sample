package com.example.android.myhometownweather;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewPager mMainViewPager;
    public static ArrayList<String> locations = new ArrayList<>();
    String currentLocation = "Ningbo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //todo:get user's permission to use location and read data
        locations.add(currentLocation);
        mMainViewPager = findViewById(R.id.main_view_pager);
        TabLayout tabDots = findViewById(R.id.tab_dots);
        tabDots.setupWithViewPager(mMainViewPager,true);
        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager(),locations.size());
        mMainViewPager.setAdapter(adapter);
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_plus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_plus:
            //todo:in the future
                //open an activity, select a location and return a string to locations list

        }
        return super.onOptionsItemSelected(item);
    }*/
}
