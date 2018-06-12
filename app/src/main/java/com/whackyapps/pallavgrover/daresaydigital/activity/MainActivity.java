package com.whackyapps.pallavgrover.daresaydigital.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.whackyapps.pallavgrover.daresaydigital.R;
import com.whackyapps.pallavgrover.daresaydigital.fragment.FirstFragment;

public class MainActivity extends AppCompatActivity {

    private FirstFragment mFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mFragment = (FirstFragment) getSupportFragmentManager().findFragmentByTag(FirstFragment.TAG);
        if(mFragment == null) {
            mFragment = FirstFragment.newInstance();
            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, mFragment, FirstFragment.TAG).commit();
        }
    }

}
