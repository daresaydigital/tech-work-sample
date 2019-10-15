package com.midnight.weatherforecast.controller

import android.util.Log
import com.midnight.weatherforecast.interfaces.INFForecast
import com.midnight.weatherforecast.interfaces.INFLoadData
import com.midnight.weatherforecast.interfaces.INFSearchCity
import com.midnight.weatherforecast.models.modelsDb.City
import com.midnight.weatherforecast.models.modelsDb.CityDao
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByGeo
import com.midnight.weatherforecast.models.modelsParam.ModelParamCurrentWeatherByName
import com.midnight.weatherforecast.models.modelsResponse.ModelCurrentWeater
import com.midnight.weatherforecast.models.modelsResponse.ModelForecast
import com.midnight.weatherforecast.utils.DispatchQueue
import com.midnight.weatherforecast.utils.TaskUnit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 *
 */
class ControllerData {
    var citiesWeater= arrayListOf<ModelCurrentWeater>()
    private object Holder { val INSTANCE=ControllerData()}
    companion object {
        val instances : ControllerData by lazy { Holder.INSTANCE }
    }

    fun getHandler(): DispatchQueue? {
       return TaskUnit.controllerDataThread
    }

    /**
     *
     */
    fun saveCity(model:ModelCurrentWeater,owner:Boolean):Boolean{
        var modelDaos=City()
        modelDaos.cityName=model.name
        modelDaos.cityId=model.id
        if (owner)
            modelDaos.owner=1
        else
            modelDaos.owner=2
        ControllerDB.instance.getDaoSession()?.insertOrReplace(modelDaos)
        return true
    }

    /**
     *
     */
    fun addToCitiesList(model:ModelCurrentWeater){
        var found=false
        for (i in 0 until citiesWeater.size){
            if (citiesWeater[i].id==model.id){
                citiesWeater[i]=model
                found=true
                break
            }
        }
        if (!found){
            citiesWeater.add(model)
        }
    }

    /**
     *
     */
    fun removeACity(model:ModelCurrentWeater) {
        val queryCity = ControllerDB.instance.getDaoSession()?.queryBuilder(City::class.java)?.where(CityDao.Properties.CityId.eq(model.id))?.build()?.unique()
        if (queryCity != null ) {
            ControllerDB.instance.getDaoSession()?.delete(queryCity)
        }

        var index=0
        for (i in 0 until citiesWeater.size){
            if (citiesWeater[i].id==model.id){
                citiesWeater.removeAt(index)
                break
            }
            index++
        }
    }

    /**
     *
     */
    fun fetchCity(model:ModelParamCurrentWeatherByName, delegate: INFLoadData, cache:Boolean, async:Boolean, isMainCity:Boolean) {
        if (async){
            ControllerAPI.instance.getCurrentWeatherByName(model, object : Callback<ModelCurrentWeater> {
                override fun onResponse(call: Call<ModelCurrentWeater>, response: Response<ModelCurrentWeater>) {
                    addCityToList(response.body()!!,isMainCity,cache)
                    delegate.onSuccess()
                }

                override fun onFailure(call: Call<ModelCurrentWeater>, t: Throwable) {
                    Log.e("onFailure", "onFailure")
                    delegate.onFail()
                }
            })
        }else{
            getHandler()?.post {
                val result=ControllerAPI.instance.getCurrentWeatherByName(model)
                addCityToList(result!!,isMainCity,cache)
                delegate.onSuccess()

            }
        }

    }

    /**
     *
     */
    fun fetchCity(model:ModelParamCurrentWeatherByGeo, delegate: INFLoadData, cache:Boolean, async:Boolean, isMainCity:Boolean) {
        if (async){
            ControllerAPI.instance.getCurrentWeatherByGPS(model, object : Callback<ModelCurrentWeater> {
                override fun onResponse(call: Call<ModelCurrentWeater>, response: Response<ModelCurrentWeater>) {
                    addCityToList(response.body()!!,isMainCity,cache)
                    delegate.onSuccess()
                }

                override fun onFailure(call: Call<ModelCurrentWeater>, t: Throwable) {
                    Log.e("onFailure", "onFailure")
                    delegate.onFail()
                }
            })
        }else{
            getHandler()?.post {
                val result=ControllerAPI.instance.getCurrentWeatherByGPS(model)
                addCityToList(result!!,isMainCity,cache)
                delegate.onSuccess()
            }
        }

    }

    /**
     *
     */
    fun loadData(delegate: INFLoadData){
        var modelLocalCity=getCachedLocalCity()
        if (modelLocalCity!=null)
            fetchCity(ModelParamCurrentWeatherByName(modelLocalCity!!.cityName,ControllerAPI.instance.API_KEY),delegate,false,false,true)

        var list=getCachedCity()
        for (item in list!!){
            fetchCity(ModelParamCurrentWeatherByName(item.cityName,ControllerAPI.instance.API_KEY),delegate,false,false,false)
        }

    }

    private fun addCityToList(model:ModelCurrentWeater,isMainCity:Boolean,isCache:Boolean){
        if (isMainCity){
            val queryList = ControllerDB.instance.getDaoSession()?.queryBuilder(City::class.java)?.where(CityDao.Properties.Owner.eq(1))?.build()?.list()
            var mainId=-1
            if (queryList==null || queryList.size==0){
                if (isCache)
                    saveCity(model,isMainCity)
            }else{
                val queryCity=queryList[0]
                mainId= queryCity?.cityId!!
                queryCity?.cityName=model.name
                queryCity?.cityId=model.id
                queryCity?.owner= 1
                ControllerDB.instance.getDaoSession()?.update(queryCity)
            }

            var found=false
            for (i in 0 until citiesWeater.size){
                if (citiesWeater[i].id==mainId){
                    citiesWeater[i]=model
                    found=true
                    break
                }
            }
            if (!found){
                citiesWeater.add(model)
            }

        }else{
            val queryCity = ControllerDB.instance.getDaoSession()?.queryBuilder(City::class.java)?.where(CityDao.Properties.CityId.eq(model.id))?.build()?.list()?.get(0)
            if (queryCity==null && isCache){
                saveCity(model,isMainCity)
            }else{
                queryCity?.cityName=model.name
                queryCity?.cityId=model.id
                queryCity?.owner= 2
                ControllerDB.instance.getDaoSession()?.update(queryCity)
            }

            var found=false
            for (i in 0 until citiesWeater.size){
                if (citiesWeater[i].id==model.id){
                    citiesWeater[i]=model
                    found=true
                    break
                }
            }
            if (!found){
                citiesWeater.add(model)
            }
        }

    }

    /**
     *
     */
    fun searchCity(model:ModelParamCurrentWeatherByName,delegate: INFSearchCity) {
        ControllerAPI.instance.getCurrentWeatherByName(model, object : Callback<ModelCurrentWeater> {
            override fun onResponse(call: Call<ModelCurrentWeater>, response: Response<ModelCurrentWeater>) {
                if (response.body()!!.id!=null)
                    delegate.onSuccess(response.body()!!)
                else
                    delegate.onFail()
            }

            override fun onFailure(call: Call<ModelCurrentWeater>, t: Throwable) {
                Log.e("onFailure", "onFailure")
                delegate.onFail()
            }
        })

    }

    /**
     *
     */
    fun fetchForecast(model:ModelParamCurrentWeatherByName,delegate: INFForecast) {
        ControllerAPI.instance.getForecastWeatherByName(model, object : Callback<ModelForecast> {
            override fun onResponse(call: Call<ModelForecast>, response: Response<ModelForecast>) {
                if (response.body()?.list!!.size>0)
                    delegate.onSuccess(response.body()!!)
                else
                    delegate.onFail()
            }

            override fun onFailure(call: Call<ModelForecast>, t: Throwable) {
                Log.e("onFailure", "onFailure")
                delegate.onFail()
            }
        })
    }

    /**
     *
     */
    fun getAllCachedCity(): MutableList<City>? {
        return ControllerDB.instance.getDaoSession()?.queryBuilder(City::class.java)?.build()?.list()
    }

    /**
     *
     */
    fun getCachedCity(): MutableList<City>? {
        return ControllerDB.instance.getDaoSession()?.queryBuilder(City::class.java)?.where(CityDao.Properties.Owner.eq(2))?.build()?.list()
    }

    /**
     *
     */
    fun getCachedLocalCity(): City? {
        return ControllerDB.instance.getDaoSession()?.queryBuilder(City::class.java)?.where(CityDao.Properties.Owner.eq(1))?.build()?.unique()
    }
}