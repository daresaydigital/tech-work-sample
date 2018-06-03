package sync;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.android.myhometownweather.Weather;

import java.net.URL;
import java.util.ArrayList;

public class WeatherAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Weather>>{
    URL urlToQuery;
    ArrayList<Weather> mWeathers;
    public WeatherAsyncTaskLoader(Context context, URL url) {
        super(context);
        urlToQuery=url;
    }

    @Override
    protected void onStartLoading() {
        if (mWeathers!=null){
            deliverResult(mWeathers);
        }else{
            forceLoad();
        }
    }

    @Override
    public ArrayList<Weather> loadInBackground() {
        if (urlToQuery==null){
            return null;
        }
        mWeathers = NetworkUtils.fetchDataByURL(urlToQuery);
        return mWeathers;
    }

    @Override
    public void deliverResult(ArrayList<Weather> data) {
        mWeathers = data;
        super.deliverResult(data);
    }
}
