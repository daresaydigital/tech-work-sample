package com.robledo.weatheri.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.robledo.weatheri.R;
import com.robledo.weatheri.utils.AnimationUtils;
import com.robledo.weatheri.utils.ViewUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DailyForecastAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_DAY = 1;
    private Context context;
    private List<com.robledo.weatheri.models.List> dailyWeatherList;

    public DailyForecastAdapter(Context context, List<com.robledo.weatheri.models.List> dailyWeatherList) {
        this.context = context;
        this.dailyWeatherList = dailyWeatherList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_DAY){
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_weather_row, parent, false);
            return new DailyWeatherHolder(itemView);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof DailyWeatherHolder) {
            DailyWeatherHolder dailyWeatherHolder = (DailyWeatherHolder) holder;
            String description = dailyWeatherList.get(position).getWeather().get(0).getDescription();
            dailyWeatherHolder.descriptionTextView.setText(String.format("%s%s", description.substring(0, 1).toUpperCase(), description.substring(1)));
            dailyWeatherHolder.lowHighTextView.setText(String.format(context.getString(R.string.cards_low_high_temp), dailyWeatherList.get(position).getTemp().getMin(), dailyWeatherList.get(position).getTemp().getMax()));
            dailyWeatherHolder.iconTextView.setText(ViewUtils.getWeatherLetterForId(dailyWeatherList.get(position).getWeather().get(0).getId()));
            dailyWeatherHolder.dayTextView.setText(getProperDate(position));
        }
    }

    private String getProperDate(int position) {
        if(position == 0) return context.getString(R.string.main_today);
        else if(position == 1) return context.getString(R.string.main_tomorrow);
        else {
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, position);
            return new SimpleDateFormat("dd/MMM").format(cal.getTime());
        }
    }

    @Override
    public int getItemCount() {
        return dailyWeatherList.size();
    }

    @Override
    public int getItemViewType (int position) {
        return TYPE_DAY;
    }


    public class DailyWeatherHolder extends RecyclerView.ViewHolder {

        TextView dayTextView;
        TextView iconTextView;
        TextView lowHighTextView;
        TextView descriptionTextView;

        DailyWeatherHolder(final View view) {
            super(view);
            this.dayTextView = view.findViewById(R.id.day_textview);
            this.iconTextView = view.findViewById(R.id.icon_textview);
            this.lowHighTextView = view.findViewById(R.id.low_high_textview);
            this.descriptionTextView = view.findViewById(R.id.description_textview);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AnimationUtils.animateExpandCollapse(view);
                }
            });
        }
    }

}
