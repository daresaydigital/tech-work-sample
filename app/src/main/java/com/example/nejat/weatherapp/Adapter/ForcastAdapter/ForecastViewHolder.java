package com.example.nejat.weatherapp.Adapter.ForcastAdapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nejat.weatherapp.POJO.ListData;
import com.example.nejat.weatherapp.R;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForecastViewHolder extends RecyclerView.ViewHolder implements ForcastAdapterContract.ForecastRowView {
    @BindView(R.id.weather_desc)
    TextView mWthrDsc;
    @BindView(R.id.dateText)
    TextView mDateTV;
    @BindView(R.id.temp)
    TextView mTempTV;
    @BindView(R.id.weather_icon)
    WeatherIconView weatherIconView;
    @BindView(R.id.itemlinearlayout)
    LinearLayout linearLayout;


    public ForecastViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    @Override
    public void setView(final String date, String weather_desc, String tmp, String icon, final ListData listData, final IItemClickListener listener) {
        setDate(date);
        setTemp(weather_desc, tmp);
        setIcon(icon);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onRowItemClick(listData);
            }
        });

    }

    public void setDate(String date) {
        mDateTV.setText(date);
    }

    public void setTemp(String weather_desc, String temp) {
        mWthrDsc.setText(weather_desc);
        mTempTV.setText(temp);
    }


    public void setIcon(String icon) {
        weatherIconView.setIconResource(icon);
        weatherIconView.setIconSize(30);
        weatherIconView.setIconColor(Color.argb(255, 252, 181, 98));
    }
}
