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
import com.midnight.weatherforecast.databinding.AdapterMainBinding;
import com.midnight.weatherforecast.interfaces.INFListViewClick;
import com.midnight.weatherforecast.utils.AppConstant;
import com.midnight.weatherforecast.utils.Utils;

public class AdapterMain extends RecyclerView.Adapter<AdapterMain.cityWeatherViewHolder>{
    private Context context;
    private INFListViewClick delegate;
    public AdapterMain(Context context,INFListViewClick delegate) {
        this.context = context;
        this.delegate=delegate;
    }


    @NonNull
    @Override
    public cityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new cityWeatherViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_main, parent, false));
    }


    @Override
    public int getItemCount() {
        return ControllerData.Companion.getInstances().getCitiesWeater().size();
    }


    @Override
    public void onBindViewHolder(@NonNull cityWeatherViewHolder holder, int position) {
        holder.binding.tvCityName.setText(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getName());
        holder.binding.tvCityTemp.setText(String.valueOf(Math.round(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getMain().getTemp())));
        holder.binding.tvCityStatus.setText(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain());
        holder.binding.tvCityStatusDesc.setText(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getDescription());
        holder.binding.tvCityHum.setText(String.format(context.getString(R.string.humidity),String.valueOf(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getMain().getHumidity())));
        holder.binding.tvCityWind.setText(String.format(context.getString(R.string.wind),String.valueOf(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWind().getSpeed())));
        holder.binding.imgIcon.setImageResource(AppConstant.iconList.get(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain()));
        holder.binding.cardview.setCardBackgroundColor(AppConstant.colorList.get(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain()));

        if ("Snow".equalsIgnoreCase(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain()) || "Fog".equalsIgnoreCase(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain())){
            holder.binding.tvCityName.setTextColor(0xFF000000);
            holder.binding.tvCityTemp.setTextColor(0xFF000000);
            holder.binding.tvCityStatus.setTextColor(0xFF000000);
            holder.binding.tvCityStatusDesc.setTextColor(0xFF000000);
            holder.binding.tvCityHum.setTextColor(0xFF000000);
            holder.binding.tvCityWind.setTextColor(0xFF000000);
            holder.binding.imgIcon.setImageResource(AppConstant.iconListBlack.get(ControllerData.Companion.getInstances().getCitiesWeater().get(position).getWeather().get(0).getMain()));
        }

        holder.binding.cardview.setOnClickListener(v -> delegate.onClickListenner(position));

        holder.binding.cardview.setOnLongClickListener(v -> {
            delegate.onLongClick(position);
            return true;
        });
    }

    /**
     *
     */
    public class cityWeatherViewHolder extends RecyclerView.ViewHolder{
        private AdapterMainBinding binding;

        public cityWeatherViewHolder(@NonNull AdapterMainBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }

}
