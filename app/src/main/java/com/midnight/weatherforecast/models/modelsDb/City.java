package com.midnight.weatherforecast.models.modelsDb;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

@Entity(nameInDb = "CITY_LIST")
public class City {
    @Id
    private Long id;
    @Property(nameInDb = "CITYID")
    @Index(unique = true)
    private int cityId;
    @Property(nameInDb = "CITYNAME")
    private String cityName;
    @Property(nameInDb = "RECOWNER")
    private int owner;

    @Generated(hash = 1705308826)
    public City(Long id, int cityId, String cityName, int owner) {
        this.id = id;
        this.cityId = cityId;
        this.cityName = cityName;
        this.owner = owner;
    }

    @Generated(hash = 750791287)
    public City() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
