package com.suroid.weatherapp.db

import android.arch.persistence.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.suroid.weatherapp.models.TemperatureModel
import com.suroid.weatherapp.models.WeatherModel
import com.suroid.weatherapp.utils.jsonify
import com.suroid.weatherapp.utils.objectify
import java.util.*


class WeatherModelConverter {

    @TypeConverter
    fun stringToWeatherModel(data: String?): WeatherModel? {
        return data?.objectify(WeatherModel::class.java)
    }

    @TypeConverter
    fun weatherModelToString(weatherModel: WeatherModel): String? {
        return weatherModel.jsonify()
    }
}

class TemperatureModelConverter {

    @TypeConverter
    fun stringToTemperatureModelList(data: String?): List<TemperatureModel>? {
        if (data == null) {
            return Collections.emptyList()
        }

        val listType = object : TypeToken<List<TemperatureModel>>() {

        }.type

        return data.objectify(listType)
    }

    @TypeConverter
    fun temperatureModelListToString(temperatureModels: List<TemperatureModel>): String? {
        return temperatureModels.jsonify()
    }
}