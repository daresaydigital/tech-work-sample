package com.midnight.weatherforecast.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.midnight.weatherforecast.R;
import com.midnight.weatherforecast.controller.ControllerData;
import com.midnight.weatherforecast.databinding.AdapterDetailBinding;
import com.midnight.weatherforecast.databinding.AdapterForecastBinding;
import com.midnight.weatherforecast.databinding.AdapterMainBinding;
import com.midnight.weatherforecast.interfaces.INFListViewClick;
import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater;
import com.midnight.weatherforecast.utils.AppConstant;
import com.midnight.weatherforecast.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class AdapterForecast extends RecyclerView.Adapter<AdapterForecast.ViewHolder>{
    private Context context;
    private INFListViewClick delegate;
    private ArrayList<ModelCurrentWeater> data=new ArrayList<>();
    public AdapterForecast(Context context, INFListViewClick delegate,ArrayList<ModelCurrentWeater> data) {
        this.context = context;
        this.delegate=delegate;
        this.data=data;
    }

    public AdapterForecast(Context context, INFListViewClick delegate) {
        this.context = context;
        this.delegate=delegate;
    }

    @Override
    public int getItemViewType(int position) {
        if (position==0){
            return 0;
        }else{
            return 1;
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType){
            case 0:
                return new cityWeatherDetailViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_detail, parent, false));
            case 1:
                return new cityWeatherForecastViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_forecast, parent, false));
                default:
                    return new cityWeatherForecastViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_search, parent, false));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        if (viewHolder instanceof cityWeatherDetailViewHolder){
            ModelCurrentWeater model=data.get(position);
            final cityWeatherDetailViewHolder holder = (cityWeatherDetailViewHolder) viewHolder;
            holder.binding.tvCityName.setText(model.getName());
            holder.binding.tvCityTemp.setText(String.valueOf(Math.round(model.getMain().getTemp())));
            holder.binding.tvCityMinTemp.setText(String.format(context.getString(R.string.min_temp),String.valueOf(Math.round(model.getMain().getTempMin()))));
            holder.binding.tvCityMaxTemp.setText(String.format(context.getString(R.string.max_temp),String.valueOf(Math.round(model.getMain().getTempMax()))));
            holder.binding.tvCityPressure.setText(String.format(context.getString(R.string.pressure),String.valueOf(Math.round(model.getMain().getPressure()))));
            holder.binding.tvCityStatus.setText(model.getWeather().get(0).getMain());
            holder.binding.tvCityStatusDesc.setText(model.getWeather().get(0).getDescription());
            holder.binding.tvCityHum.setText(String.format(context.getString(R.string.humidity),String.valueOf(model.getMain().getHumidity())));
            holder.binding.tvCityWind.setText(String.format(context.getString(R.string.wind),String.valueOf(model.getWind().getSpeed())));
            holder.binding.imgIcon.setImageResource(AppConstant.iconList.get(model.getWeather().get(0).getMain()));
            holder.binding.cardview.setCardBackgroundColor(AppConstant.colorList.get(model.getWeather().get(0).getMain()));

            if ("Snow".equalsIgnoreCase(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain()) || "Fog".equalsIgnoreCase(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain())){
                holder.binding.tvCityName.setTextColor(0xFF000000);
                holder.binding.tvCityTemp.setTextColor(0xFF000000);
                holder.binding.tvCityStatus.setTextColor(0xFF000000);
                holder.binding.tvCityStatusDesc.setTextColor(0xFF000000);
                holder.binding.tvCityHum.setTextColor(0xFF000000);
                holder.binding.tvCityWind.setTextColor(0xFF000000);
                holder.binding.imgIcon.setImageResource(AppConstant.iconListBlack.get(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain()));
            }


        }else if (viewHolder instanceof cityWeatherForecastViewHolder){
            ModelCurrentWeater model=data.get(position);
            final cityWeatherForecastViewHolder holder = (cityWeatherForecastViewHolder) viewHolder;
            holder.binding.tvDay.setText(Utils.getDayOfWeek(model.getDt()));
            holder.binding.tvDate.setText(Utils.getDateFormat(model.getDt()));
            holder.binding.tvCityTemp.setText(String.valueOf(Math.round(model.getMain().getTemp())));
            holder.binding.tvCityStatus.setText(model.getWeather().get(0).getMain());
            holder.binding.imgIcon.setImageResource(AppConstant.iconList.get(model.getWeather().get(0).getMain()));
            holder.binding.cardview.setCardBackgroundColor(AppConstant.colorList.get(model.getWeather().get(0).getMain()));

            if ("Snow".equalsIgnoreCase(model.getWeather().get(0).getMain()) || "Fog".equalsIgnoreCase(model.getWeather().get(0).getMain())){
                holder.binding.tvDay.setTextColor(0xFF000000);
                holder.binding.tvDate.setTextColor(0xFF000000);
                holder.binding.tvCityTemp.setTextColor(0xFF000000);
                holder.binding.tvCityStatus.setTextColor(0xFF000000);
                holder.binding.imgIcon.setImageResource(AppConstant.iconListBlack.get(model.getWeather().get(0).getMain()));
            }
            int hours= Utils.getHourOfDay(model.getDt());
            if (hours>19 || hours<6){
                holder.binding.cardview.setCardBackgroundColor(0xFF1A2636);
                holder.binding.imgIcon.setImageResource(AppConstant.iconListMight.get(model.getWeather().get(0).getMain()));
            }

        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    /**
     *
     */
    public class cityWeatherForecastViewHolder extends ViewHolder{
        private AdapterForecastBinding binding;

        public cityWeatherForecastViewHolder(@NonNull AdapterForecastBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }

    public class cityWeatherDetailViewHolder extends ViewHolder{
        private AdapterDetailBinding binding;

        public cityWeatherDetailViewHolder(@NonNull AdapterDetailBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }

    public void addData(ModelCurrentWeater modelCurrentWeater,int position){
        this.data.add(position,modelCurrentWeater);
        notifyDataSetChanged();
    }

    public void addData(List<ModelCurrentWeater> data){
        this.data.addAll(data);
        notifyDataSetChanged();
    }

}
