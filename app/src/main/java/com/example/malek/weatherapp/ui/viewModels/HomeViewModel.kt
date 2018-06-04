package com.example.malek.weatherapp.ui.viewModels

import android.content.Context
import android.location.Location
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.malek.weatherapp.models.CurrentWeather
import com.example.malek.weatherapp.utils.ApiManager
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.io.FileOutputStream

class HomeViewModelImp : HomeViewModel {
    val filename = "weather.json"
    val TAG = HomeViewModelImp::class.java.simpleName

    override fun getCurrentWeatherObservable(location: Location, context: Context) =
            ApiManager
                    .apiService
                    .getCurrentWeather(lat = location.latitude.toString(), lon = location.longitude.toString(), apiKey = ApiManager.apiKey)
                    .subscribeOn(Schedulers.io())
                    .flatMap {
                        if (it.cod == 200) {
                            // save current weather to a file
                            saveWeatherToFile(it, context)
                            return@flatMap Single.just(it)
                        } else if (it.message != null) {
                            return@flatMap Single.error<Throwable>(Throwable(it.message))
                        } else {
                            return@flatMap Single.error<Throwable>(Throwable("undefined Response"))
                        }
                    }
                    .onErrorResumeNext {
                        // on error while geting forecast from api return local file
                        Log.e(TAG, it.toString())
                        getCurrentWeatherFormFile(context)

                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .cast(CurrentWeather::class.java)


    fun getCurrentWeatherFormFile(context: Context) =
            Single.fromCallable {
                val file = File(context.filesDir, filename)
                val fileInputStream = context.openFileInput(filename)
                val fileContent = ByteArray(file.length().toInt())
                fileInputStream.read(fileContent)
                val json = String(fileContent)
                Log.e(TAG, "file$json")
                fileInputStream.close()
                return@fromCallable Gson().fromJson(json, CurrentWeather::class.java)
            }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

    private fun saveWeatherToFile(currentWeather: CurrentWeather, context: Context) {
        Observable
                .fromCallable {
                    val json = Gson().toJson(currentWeather)
                    val outputStream: FileOutputStream = context.openFileOutput(filename, AppCompatActivity.MODE_PRIVATE)
                    outputStream.write(json.toByteArray())
                    outputStream.close()

                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.e(TAG, "file saved")
                }, {
                    Log.e(TAG, it.toString())
                })
    }

}

interface HomeViewModel {
    fun getCurrentWeatherObservable(location: Location, context: Context): Single<CurrentWeather>
}