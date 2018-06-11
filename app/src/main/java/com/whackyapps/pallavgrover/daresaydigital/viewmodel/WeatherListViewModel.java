package com.whackyapps.pallavgrover.daresaydigital.viewmodel;

import android.app.Application;
import android.arch.core.util.Function;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.annotation.NonNull;


import com.whackyapps.pallavgrover.daresaydigital.data.RemoteDataSource;
import com.whackyapps.pallavgrover.daresaydigital.data.model.Weather;
import com.whackyapps.pallavgrover.daresaydigital.util.Constants;


public class WeatherListViewModel extends AndroidViewModel {

    private final MutableLiveData<String> mFoodPageIndex = new MutableLiveData<>();

    private final LiveData<Weather> mWeather;

    private final LiveData<Weather> mLongLat;

    private RemoteDataSource mFoodDataRespository = null;
    private Context mContext;

    private WeatherListViewModel(Application application, RemoteDataSource weatherDataRepository, Context context) {
        super(application);
        mFoodDataRespository = weatherDataRepository;
        mContext=context;
        mWeather = Transformations.switchMap(mFoodPageIndex, new Function<String, LiveData<Weather>>() {
            @Override
            public LiveData<Weather> apply(String input) {
                return mFoodDataRespository.getWeatherList(input);
            }
        });

        mLongLat = Transformations.switchMap(mFoodPageIndex, new Function<String, LiveData<Weather>>() {
            @Override
            public LiveData<Weather> apply(String input) {
                return mFoodDataRespository.getWeatherList(input);
            }
        });
    }

    public LiveData<Weather> getWeather() {
        return mWeather;
    }

    public void refreshWeatherData() {
        mFoodPageIndex.setValue(Constants.CITY_NAME);
    }


    public LiveData<Boolean> getLoadMoreState() {
        return mFoodDataRespository.isLoadingWeather();
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final RemoteDataSource mWeatherDataRespository;

        private final Context mContext;

        public Factory(@NonNull Application application, RemoteDataSource mWeatherDataRespository,Context context) {
            mApplication = application;
            this.mWeatherDataRespository = mWeatherDataRespository;
            mContext = context;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new WeatherListViewModel(mApplication, mWeatherDataRespository,mContext);
        }
    }
}
