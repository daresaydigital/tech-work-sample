package com.example.nejat.weatherapp.WeatherData;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Geocoder;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nejat.weatherapp.Adapter.ForcastAdapter.ForcastAdapterPresenter;
import com.example.nejat.weatherapp.Adapter.ForcastAdapter.ForecastAdapter;
import com.example.nejat.weatherapp.Adapter.ForcastAdapter.IItemClickListener;
import com.example.nejat.weatherapp.POJO.ListData;
import com.example.nejat.weatherapp.R;
import com.example.nejat.weatherapp.Utils.Common;
import com.example.nejat.weatherapp.Utils.settings;
import com.github.pwittchen.weathericonview.WeatherIconView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.xw.repo.BubbleSeekBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherDataActivity extends AppCompatActivity implements WeatherDataContract.WeatherDataView, IItemClickListener, View.OnClickListener {

    WeatherDataPresenter weatherDataPresenter;
    ForecastAdapter forcastAdapter;
    boolean mLocationPermissionGranted = false;
    private final int LOCATION_PERMISSION_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private String[] arr = new String[5];
    List<ListData> dailyDataPre = new ArrayList<>();

    @BindView(R.id.dateText)
    TextView mDateTV;
    @BindView(R.id.windTxt)
    TextView mWindTV;
    @BindView(R.id.title)
    TextView mTitleTV;
    @BindView(R.id.tempText)
    TextView mTempTV;
    @BindView(R.id.icon)
    WeatherIconView mIconIV;
    @BindView(R.id.editBtn)
    ImageView mEditBtn;
    @BindView(R.id.lowerLayout)
    RecyclerView mRecyclerView;
    @BindView(R.id.bubbleSeekBar)
    BubbleSeekBar bubbleSeekBar;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_weather_data);


        ButterKnife.bind(this);
        ButterKnife.setDebug(true);
        internetKontrol();
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please wait a sec");
        progressDialog.show();


        weatherDataPresenter = new WeatherDataPresenter(this, this);

        weatherDataPresenter.checkGoogleAPI();

        mEditBtn.setOnClickListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        getDeviceLocation();

    }

    @Override
    public void displayWeeklyData(final List<ListData> listData) {
        forcastAdapter = new ForecastAdapter(this, listData, this);
        Log.i("listDAta", listData.get(0).getCityName());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(forcastAdapter);
        displayDataNow(listData.get(0));
    }

    @Override
    public void displaySeekBarData(final List<ListData> dailyData) {
        bubbleSeekBar.getConfigBuilder().sectionCount(4).seekStepSection().trackSize(6).hideBubble().sectionTextSize(15).build();


        bubbleSeekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                for (int i = 0; i < dailyData.size(); i++) {
                    array.put(i, dailyData.get(i).getDate().substring(11, 16));
                    dailyDataPre.add(dailyData.get(i));

                }
                for (int i = 0; i < array.size() && i < 5; i++) {
                    arr[i] = array.get(i);
                }
                return array;
            }
        });

        bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                Log.i("progressChange", progress + "");
                weatherDataPresenter.progressChange(arr, progress, dailyDataPre);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                Log.i("progressAction", progress + "");


            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                Log.i("progressFinally", progress + "");


            }
        });
    }

    @Override
    public void displayDataNow(ListData listData) {
        progressDialog.cancel();
        if (listData != null) {
            Log.i("listDAtaNoww", listData.getTemp()+"");

            mDateTV.setText(new ForcastAdapterPresenter(listData, this).changeDateFormat(listData.getDate(), "EEEE MMMM dd"));
            mTitleTV.setText(listData.getCityName());
            mWindTV.setText(listData.getSpeed() + "km/hr");
            mTempTV.setText(((int) listData.getTemp()) + "\u00b0");
            Calendar cal = Calendar.getInstance();
            mIconIV.setIconResource(new ForcastAdapterPresenter(listData, this).setWeatherIcon(listData.getIcon(), cal.get(Calendar.HOUR_OF_DAY)));
            mIconIV.setIconSize(50);
            mIconIV.setIconColor(Color.argb(255, 255, 130, 30));

        }
    }

    public void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            getDeviceLocation();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                    getDeviceLocation();
                }
            }
        }

    }

    public void getDeviceLocation() {

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }

            mFusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {

                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if (task.isSuccessful()) {

                        if (task.getResult() != null) {

                            double lat = task.getResult().getLatitude();
                            double lon = task.getResult().getLongitude();
                            Common common = new Common();
                            common.setLat(lat);
                            common.setLon(lon);
                            weatherDataPresenter.getWeatherData(lat, lon);

                        }
                    }
                }
            });
        }
    }

    @Override
    public void onRowItemClick(ListData listData) {
        String date = listData.getDate().substring(0, 10);
        weatherDataPresenter.getDaily(date);
        displayDataNow(listData);
    }

    @Override
    public void onClick(View v) {
        if (v == mEditBtn) {
            showLocationChooseDialog();
        }
    }

    private void showLocationChooseDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(getApplicationContext());
        alertDialog.setTitle(R.string.alert_changeLocation_title);
        alertDialog.setMessage(R.string.alert_changeLocation_message);
        alertDialog.setView(edittext);
        alertDialog.setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String location = edittext.getText().toString();
                Geocoder geocoder = new Geocoder(getApplicationContext());
                weatherDataPresenter.getDataByLocation(location, geocoder);

            }
        });
        alertDialog.setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public void internetKontrol() {
        settings settings = new settings(this);
        if (!(settings.isNetworkAvailable())) {
            settings.showNoConnectionDialog();
        }
    }


}
