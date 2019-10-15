package com.midnight.weatherforecast;

import android.os.Build;
import android.os.Looper;

import com.midnight.weatherforecast.controller.ControllerAPI;
import com.midnight.weatherforecast.core.Loader;
import com.midnight.weatherforecast.interfaces.INFApi;
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByName;
import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;

import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.M,application = Loader.class,shadows = MyNetworkSecurityPolicy.class)
public class TestGetCurrentCityWeather {
    private String finalResult="";
    private MockWebServer mockWebServer=new MockWebServer();
    private INFApi apiService;
    @Before
    public void startTest(){
        try {
            mockWebServer.start();
            apiService = new Retrofit.Builder()
                    .baseUrl(mockWebServer.url("/"))
                    .build()
                    .create(INFApi.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void getCurrentByName() {
        Halt checker=new Halt(10000,1000);
        shadowOf(Looper.getMainLooper()).idle();

        ControllerAPI.Companion.getInstance().getCurrentWeatherByName(new ModelParamCurrentWeatherByName("Stockholm", ControllerAPI.Companion.getInstance().getAPI_KEY()), new Callback<ModelCurrentWeater>() {
            @Override
            public void onResponse(Call<ModelCurrentWeater> call, Response<ModelCurrentWeater> response) {
                finalResult=response.body().getName();
            }

            @Override
            public void onFailure(Call<ModelCurrentWeater> call, Throwable t) {
                finalResult="-1";
            }
        });

        checker.execHalt(new ConditionCheck() {
            @Override
            public boolean condition() {
                if (!finalResult.isEmpty()){
                    Assert.assertEquals("Stockholm",finalResult);
                    return false;
                }
                return true;
            }

            @Override
            public void finalyAssert() {
                Assert.assertEquals("Stockholm","-1");
            }
        });

        Robolectric.flushForegroundThreadScheduler();
        Robolectric.flushBackgroundThreadScheduler();

    }

    @After
    public void endTest(){
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}