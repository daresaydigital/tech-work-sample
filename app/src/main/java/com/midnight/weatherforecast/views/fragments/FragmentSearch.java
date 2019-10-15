package com.midnight.weatherforecast.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.midnight.weatherforecast.R;
import com.midnight.weatherforecast.controller.ControllerAPI;
import com.midnight.weatherforecast.controller.ControllerData;
import com.midnight.weatherforecast.databinding.FragmentAddCityBinding;
import com.midnight.weatherforecast.interfaces.INFSearchCity;
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByName;
import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater;
import com.midnight.weatherforecast.utils.Utils;
import com.midnight.weatherforecast.views.MainActivity;
import com.midnight.weatherforecast.views.adapters.AdapterSearch;

import org.jetbrains.annotations.NotNull;

import okhttp3.internal.Util;

public class FragmentSearch extends Fragment {
    public static String TAG= FragmentSearch.class.getName();
    private FragmentAddCityBinding binding;
    private AdapterSearch adapterSearch;
    private Handler typeingHandler;
    private TextWatcher txtWatcher;
    private String cityName="";
    private Runnable searchRunnable=new Runnable() {
        @Override
        public void run() {
            binding.pbLoading.setVisibility(View.VISIBLE);
            ControllerData.Companion.getInstances().searchCity(new ModelParamCurrentWeatherByName(cityName.toString(), ControllerAPI.Companion.getInstance().getAPI_KEY()), new INFSearchCity() {
                @Override
                public void onSuccess(@NotNull ModelCurrentWeater model) {
                    Log.d("Search","done : "+model.getName());
                    if (getView()!=null && model!=null)
                        Utils.runOnUiThread(() -> {
                            binding.pbLoading.setVisibility(View.GONE);
                            adapterSearch.update(model);
                        });
                }

                @Override
                public void onFail() {
                    Log.d("Search"," Fail ");
                }
            });
        }
    };
    public static FragmentSearch getInstance(){
        return new FragmentSearch();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initValue();
    }


    /**
     *
     */
    private void initView(){
        adapterSearch=new AdapterSearch(getContext());
        binding.rclMain.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rclMain.setAdapter(adapterSearch);
    }

    private void initValue(){
        typeingHandler=new Handler();
        txtWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("Search","Typing  ");
                typeingHandler.removeCallbacks(searchRunnable);
                cityName=s.toString();
                if (!cityName.isEmpty())
                    typeingHandler.postDelayed(searchRunnable,1500);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        binding.edtSearch.addTextChangedListener(txtWatcher);

        binding.fabAdd.setOnClickListener(v -> {
            if (adapterSearch.getModel()!=null){
                ControllerData.Companion.getInstances().saveCity(adapterSearch.getModel(),false);
                ControllerData.Companion.getInstances().addToCitiesList(adapterSearch.getModel());
                ((MainActivity)getActivity()).onBackPressed();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_city, container, false);
        return binding.getRoot();
    }



}
