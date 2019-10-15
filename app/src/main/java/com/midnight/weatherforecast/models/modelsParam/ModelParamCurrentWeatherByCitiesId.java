package com.midnight.weatherforecast.models.modelsParam;

import java.util.ArrayList;

/**
 *
 */
public class ModelParamCurrentWeatherByCitiesId {
    private ArrayList<String> ids=new ArrayList<>();
    private String key;

    public ModelParamCurrentWeatherByCitiesId(String cityId, String key) {
        this.ids.add(cityId);
        this.key = key;
    }

    public String getCityId() {
        String result="";
        synchronized (ids) {
            for (int index = 0; index < ids.size(); index++) {
                result = result + ids.get(index);
                if (index < ids.size() - 1)
                    result = result + ",";
            }
        }
        return result;
    }

    public void setCityId(String cityId) {
        this.ids.add(cityId);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
