package com.midnight.weatherforecast.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.midnight.weatherforecast.R;
import com.midnight.weatherforecast.controller.ControllerAPI;
import com.midnight.weatherforecast.controller.ControllerData;
import com.midnight.weatherforecast.databinding.FragmentDetailBinding;
import com.midnight.weatherforecast.interfaces.INFForecast;
import com.midnight.weatherforecast.interfaces.INFListViewClick;
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByName;
import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater;
import com.midnight.weatherforecast.models.modelsResponse.ModelForecast;
import com.midnight.weatherforecast.utils.Utils;
import com.midnight.weatherforecast.views.adapters.AdapterForecast;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FragmentDetail extends Fragment implements INFListViewClick {
    public static String TAG=FragmentDetail.class.getName();
    private FragmentDetailBinding binding;
    private AdapterForecast adapter;
    public static FragmentDetail getInstance(Bundle bundle){
        FragmentDetail model=new FragmentDetail();
        model.setArguments(bundle);
        return model;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initClick();
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false);
        return binding.getRoot();
    }

    private void initView(){

    }

    private void initClick(){

    }

    private void initData() {
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            binding.pbLoading.setVisibility(View.VISIBLE);
            ModelCurrentWeater modelCurrentWeater= (ModelCurrentWeater) bundle.getSerializable("current");
            adapter=new AdapterForecast(getContext(),this);
            binding.rclMain.setLayoutManager(new LinearLayoutManager(getContext()));
            binding.rclMain.setAdapter(adapter);
            adapter.addData(modelCurrentWeater,0);
            ControllerData.Companion.getInstances().fetchForecast(new ModelParamCurrentWeatherByName(modelCurrentWeater.getName(), ControllerAPI.Companion.getInstance().getAPI_KEY()), new INFForecast() {
                @Override
                public void onSuccess(@NotNull ModelForecast model) {
                    if (getView()!=null)
                        Utils.runOnUiThread(() -> {
                            binding.pbLoading.setVisibility(View.GONE);
                            adapter.addData(model.getList());
                        });
                }

                @Override
                public void onFail() {
                    if (getView()!=null)
                        Utils.runOnUiThread(() -> {
                            binding.pbLoading.setVisibility(View.GONE);
                        });

                }
            });
        }

    }

    @Override
    public void onClickListenner(int position) {

    }

    @Override
    public void onLongClick(int position) {

    }
}

