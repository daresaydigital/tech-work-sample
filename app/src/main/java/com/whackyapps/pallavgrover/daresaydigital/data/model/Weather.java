package com.whackyapps.pallavgrover.daresaydigital.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class Weather implements Serializable{
    private Coordinates coord;
    private Main main;
    private String name;
    private Sys sys;
    private Wind wind;
    @SerializedName("clouds")
    private Cloud cloud;
    private long dt;
    private int cod;
    private String description;
    private List<Weatherinfor> weather;

    public Cloud getCloud() {
        return cloud;
    }

    public String getName() {
        return name;
    }

    public long getDt() {
        return dt;
    }

    public long getCod() {
        return cod;
    }

    public Sys getSys() {
        return sys;
    }

    public Wind getWind() {
        return wind;
    }

    public Main getMain() {
        return main;
    }

    public List<Weatherinfor> getWeather() {
        return weather;
    }
    public class Cloud {

        @SerializedName("all")
        private int all;
        public int getAll() {
            return all;
        }
    }
    public class Main {
        private double temp;
        private double humidity;
        private double pressure;

        public double getTemp() {
            return temp;
        }

        public int getHumidity() {
            return (int) humidity;
        }

        public double getPressure() {
            return pressure;
        }
    }

    public static class Weatherinfor implements Serializable{
        private int id;
        private String description;
        private String icon;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }



        public String getDescription() {
            return description;
        }

        public void setDescription(String desc) {
            this.description = desc;
        }

        public int getId() {
            return id;
        }
    }

    public class Wind {
        private float speed;
        @SerializedName("deg")
        private float direction;

        public float getSpeed() {
            return speed;
        }

        public int getDirection() {
            return (int) direction;
        }
    }

    public class Sys {
        private long sunrise;
        private long sunset;
        private String country;

        public long getSunrise() {
            return sunrise;
        }

        public long getSunset() {
            return sunset;
        }

        public String getCountry() {
            return country;
        }
    }

    public class Coordinates {
        @SerializedName("lat")
        private double latitude;
        @SerializedName("lon")
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}