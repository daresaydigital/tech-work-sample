package com.midnight.weatherforecast.intersepts;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class GlobalIntersepts implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        okhttp3.Response response = chain.proceed(request);
        if (response.code() == 403) {
            //We have to show some error
            return response;
        }else {
            return response;
        }

    }
}
