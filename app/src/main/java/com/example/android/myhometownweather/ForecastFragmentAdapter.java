package com.example.android.myhometownweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ForecastFragmentAdapter extends RecyclerView.Adapter<ForecastFragmentAdapter.ForecastViewHolder> {
    Context mContext;
    ArrayList<Weather> mForecasts;
    public ForecastFragmentAdapter(Context context){
        mContext = context;
    }
    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView rootView = (CardView) LayoutInflater.from(mContext).inflate(R.layout.forecast_item_view,parent,false);
        ForecastViewHolder holder = new ForecastViewHolder(rootView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        Weather forecast = mForecasts.get(position+1);
        if (forecast!= null){
            long date = forecast.getmDate();
            String dayOfWeek = WeatherDataTransUtils.transformDate(date);
            holder.mDateTv.setText(dayOfWeek);
            int minTemp = (int) Math.round(forecast.getmMinTemp());
            int maxTemp =(int)Math.round(forecast.getmMaxTemp());
            holder.mMinTempTv.setText(String.valueOf(minTemp)+"°");
            holder.mMaxTempTv.setText(String.valueOf(maxTemp)+"°");
            int id = forecast.getmIconId();
            int iconResId = WeatherDataTransUtils.transformIdToSmallImage(id);
            holder.mForecastWeatherIcon.setImageResource(iconResId);
            Log.i("forecastadapter","i am called at position:"+position);
        }
    }

    @Override
    public int getItemCount() {
        if (mForecasts!=null){
            return mForecasts.size()-1;
        }
        return 0;
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView mDateTv, mMinTempTv, mMaxTempTv;
        ImageView mForecastWeatherIcon;
        public ForecastViewHolder(View itemView) {
            super(itemView);
            mDateTv = itemView.findViewById(R.id.date_tv);
            mMinTempTv = itemView.findViewById(R.id.min_temp_tv);
            mMaxTempTv = itemView.findViewById(R.id.max_temp_tv);
            mForecastWeatherIcon = itemView.findViewById(R.id.forecast_weather_icon);
        }
    }

    public void setmForecasts(ArrayList<Weather> mForecasts) {
        this.mForecasts = mForecasts;
    }
}
