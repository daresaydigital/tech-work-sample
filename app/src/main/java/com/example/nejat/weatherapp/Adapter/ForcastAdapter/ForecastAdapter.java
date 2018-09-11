package com.example.nejat.weatherapp.Adapter.ForcastAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.nejat.weatherapp.POJO.ListData;
import com.example.nejat.weatherapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastViewHolder> {
    private Context context;
    private List<ListData> listData;
    private ForcastAdapterPresenter presenter;
    private final IItemClickListener listener;

    public ForecastAdapter(Context context, List<ListData> listData,IItemClickListener listener) {
        this.context = context;
        this.listData = listData;
        this.listener = listener;
        presenter = new ForcastAdapterPresenter(context,listData,listener);
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_list_item,parent,false);
        ButterKnife.bind(this,view);
        return new ForecastViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        presenter.onBindForecastRowViewAtPosition(position,holder);
    }

    @Override
    public int getItemCount() {
        return presenter.ForecastRowsCount();
    }
}
