package com.midnight.weatherforecast.views.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
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
import com.midnight.weatherforecast.controller.ControllerLocation;
import com.midnight.weatherforecast.controller.ControllerPermission;
import com.midnight.weatherforecast.databinding.FragmentMainBinding;
import com.midnight.weatherforecast.interfaces.INFListViewClick;
import com.midnight.weatherforecast.interfaces.INFLoadData;
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByGeo;
import com.midnight.weatherforecast.utils.Utils;
import com.midnight.weatherforecast.views.MainActivity;
import com.midnight.weatherforecast.views.adapters.AdapterMain;

public class FragmentMain extends Fragment implements LocationListener, INFListViewClick {
    public static String TAG=FragmentMain.class.getName();
    private FragmentMainBinding binding;
    private AdapterMain adapterMain;
    private ControllerLocation locationUtils;
    public static FragmentMain getInstance(){
        return new FragmentMain();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initClick();
        getPermission();
        initData();
    }

    private void initData(){
        handlerLoading(true);
        ControllerData.Companion.getInstances().loadData(new INFLoadData() {
            @Override
            public void onSuccess() {
                if (getView()!=null)
                    Utils.runOnUiThread(() -> {
                        handlerLoading(false);
                        adapterMain.notifyDataSetChanged();
                    });
            }

            @Override
            public void onFail() {

            }
        });
    }

    /**
     *
     */
    private void initView(){
        adapterMain=new AdapterMain(getContext(),this);
        binding.rclMain.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rclMain.setAdapter(adapterMain);
    }

    private void initClick(){
        binding.fabAdd.setOnClickListener(v -> ((MainActivity)getActivity()).openAddCityFragment());
    }



    /**
     *
     */
    private void getPermission(){
        if (ControllerPermission.Companion.getInstance().getLocationPermission(getContext(),getActivity())){
            getLocation();
        }
    }

    /**
     *
     */
    public void getLocation(){
        locationUtils = new ControllerLocation(getActivity(),this);
        locationUtils.requestUpdates();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false);
        return binding.getRoot();
    }

    @Override
    public void onLocationChanged(Location location) {
        ControllerData.Companion.getInstances().fetchCity(new ModelParamCurrentWeatherByGeo(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), ControllerAPI.Companion.getInstance().getAPI_KEY()), new INFLoadData() {
            @Override
            public void onSuccess() {
                if (getView()!=null)
                    Utils.runOnUiThread(() -> adapterMain.notifyDataSetChanged());
            }

            @Override
            public void onFail() {

            }
        },true,true,true);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onClickListenner(int position) {
        Bundle bnd=new Bundle();
        bnd.putSerializable("current",ControllerData.Companion.getInstances().getCitiesWeater().get(position));
        ((MainActivity)getActivity()).openDetailFragment(bnd);
    }

    @Override
    public void onLongClick(int position) {
        if (position==0)
            return;
        new AlertDialog.Builder(getContext())
                .setTitle(getString(R.string.dialog_delete_title))
                .setMessage(getString(R.string.dialog_delete_message))
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    ControllerData.Companion.getInstances().removeACity(ControllerData.Companion.getInstances().getCitiesWeater().get(position));
                    adapterMain.notifyDataSetChanged();
                })
                .setNegativeButton(android.R.string.no, (dialog, which) -> {

                })
                .setNegativeButton(android.R.string.no, null)
                .show();

    }

    public void handlerLoading(boolean isShow){
        if (isShow){
            binding.pbLoading.setVisibility(View.VISIBLE);
        }else {
            binding.pbLoading.setVisibility(View.GONE);
        }
    }
}
