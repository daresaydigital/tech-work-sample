package com.midnight.weatherforecast.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.midnight.weatherforecast.R;
import com.midnight.weatherforecast.controller.ControllerData;
import com.midnight.weatherforecast.databinding.AdapterSearchBinding;
import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater;
import com.midnight.weatherforecast.utils.AppConstant;
import com.midnight.weatherforecast.utils.Utils;

public class AdapterSearch extends RecyclerView.Adapter<AdapterSearch.cityWeatherViewHolder>{
    private Context context;
    private ModelCurrentWeater model;

    public AdapterSearch(Context context) {
        this.context = context;
    }

    public AdapterSearch(Context context, ModelCurrentWeater model) {
        this.context = context;
        this.model = model;
    }

    @NonNull
    @Override
    public cityWeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new cityWeatherViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.adapter_search, parent, false));
    }


    @Override
    public int getItemCount() {
        if (model==null)
            return 0;
        else
            return 1;
    }


    @Override
    public void onBindViewHolder(@NonNull cityWeatherViewHolder holder, int position) {
        holder.binding.tvCityName.setText(model.getName());
        holder.binding.tvCityTemp.setText(String.valueOf(Math.round(model.getMain().getTemp())));
        holder.binding.tvCityStatus.setText(model.getWeather().get(0).getMain());
        holder.binding.imgIcon.setImageResource(AppConstant.iconList.get(model.getWeather().get(0).getMain()));
        holder.binding.cardview.setCardBackgroundColor(AppConstant.colorList.get(model.getWeather().get(0).getMain()));

        if ("Snow".equalsIgnoreCase(model.getWeather().get(0).getMain()) || "Fog".equalsIgnoreCase(model.getWeather().get(0).getMain())){
            holder.binding.tvCityName.setTextColor(0xFF000000);
            holder.binding.tvCityTemp.setTextColor(0xFF000000);
            holder.binding.tvCityStatus.setTextColor(0xFF000000);
            holder.binding.imgIcon.setImageResource(AppConstant.iconListBlack.get(model.getWeather().get(0).getMain()));
        }

    }

    /**
     *
     */
    public class cityWeatherViewHolder extends RecyclerView.ViewHolder{
        private AdapterSearchBinding binding;

        public cityWeatherViewHolder(@NonNull AdapterSearchBinding itemView) {
            super(itemView.getRoot());
            binding=itemView;
        }
    }

    public void update(ModelCurrentWeater mode){
        this.model=mode;
        notifyDataSetChanged();
    }

    public ModelCurrentWeater getModel() {
        return model;
    }
}
