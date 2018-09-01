package com.robledo.weatheri.presenters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.robledo.weatheri.interfaces.MainViewContract;
import com.robledo.weatheri.models.CurrentWeather;
import com.robledo.weatheri.models.WeatherForecast;
import com.robledo.weatheri.network.NetworkService;
import com.robledo.weatheri.utils.MathUtils;
import com.robledo.weatheri.utils.NetworkConstants;
import com.robledo.weatheri.utils.ViewUtils;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class MainPresenter implements MainViewContract.Presenter {

    private final NetworkService networkService;
    private final MainViewContract.View view;
    private CompositeSubscription subscriptions;
    private String currentCityCode = "";

    public MainPresenter(NetworkService networkService, MainViewContract.View view) {
        this.networkService = networkService;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    /**
     * Method to start downloading all data to display to the user, location has been already given by
     * the activity which handles LocationManager
     * 1. Get Current Weather
     * 2. Get Weather Forecast for the next 16 days
     */
    @Override
    public void viewIsReady(String lastLocationCity, String countryCode) {
        String cityCode = lastLocationCity.concat(",").concat(countryCode);
        //Only update results if city has changed
        if(!currentCityCode.equals(cityCode)) {
            currentCityCode = cityCode;
            getCurrentWeather(cityCode);
            getDailyForecast(cityCode);
            view.showCityName(lastLocationCity);
        }
    }

    /**
     * Gets the current weather from the API, RxJava+Retrofit is used to make the call.
     */
    @Override
    public void getCurrentWeather(String lastLocationCity) {
        view.showWait();

        Subscription subscription = networkService.getCurrentWeather(lastLocationCity, NetworkConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends CurrentWeather>>() {
                    @Override
                    public Observable<? extends CurrentWeather> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<CurrentWeather>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.removeWait();
                        view.onFailure(e.getMessage());
                    }

                    @Override
                    public void onNext(CurrentWeather currentWeather) {
                        view.removeWait();
                        String description = currentWeather.getWeather().get(0).getDescription();
                        view.showCurrentWeather(currentWeather.getMain().getTemp(), //Temperature as a Double
                                description.substring(0, 1).toUpperCase() + description.substring(1), //Description of weather as String
                                ViewUtils.getWeatherLetterForId(currentWeather.getWeather().get(0).getId())); // Letter that represents the weather

                    }
                });

        subscriptions.add(subscription);
    }

    /**
     * Gets the daily forecast of 16 days from the API, RxJava+Retrofit is used to make the call.
     */
    @Override
    public void getDailyForecast(String lastLocationCity) {
        view.showWait();

        Subscription subscription = networkService.getDailyForecast(lastLocationCity, NetworkConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends WeatherForecast>>() {
                    @Override
                    public Observable<? extends WeatherForecast> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<WeatherForecast>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        view.removeWait();
                    }

                    @Override
                    public void onNext(WeatherForecast weatherForecast) {
                        view.removeWait();
                        view.showWeatherForecast(weatherForecast.getList());
                    }
                });

        subscriptions.add(subscription);
    }

    /**
     * Unsubscribe the RxJava subscription when the view enters OnStop method.
     */
    public void onStop() {
        if(subscriptions != null && !subscriptions.isUnsubscribed())
        subscriptions.unsubscribe();
    }

}
